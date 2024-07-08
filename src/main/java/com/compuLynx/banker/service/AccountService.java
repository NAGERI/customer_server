package com.compuLynx.banker.service;

import com.compuLynx.banker.model.Account;
import com.compuLynx.banker.model.Customer;
import com.compuLynx.banker.model.Transaction;
import com.compuLynx.banker.repo.AccountRepository;
import com.compuLynx.banker.repo.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public List<Account> getAccountsByCustomer(Customer customer) {
        return accountRepository.findByCustomer(customer);
    }

    public Account saveAccount(Account account) {
        // TODO handle logging of errors

        try {

            return accountRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            // Handle the case where the account ID already exists
            // You can log an error, throw a specific exception, or take appropriate action
            throw new IllegalArgumentException("Account with ID " + account.getId() + " already exists.");
        }
    }

    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) throws InsufficientFundsException {
        try {
            // Retrieve sender and receiver accounts
            Optional<Account> optionalFromAccount = accountRepository.findById(fromAccountId);
            Optional<Account> optionalToAccount = accountRepository.findById(toAccountId);

            if (optionalFromAccount.isPresent() && optionalToAccount.isPresent() && transactionRepository != null) {
                Account fromAccount = optionalFromAccount.get();
                Account toAccount = optionalToAccount.get();

                // Record transactions
                Transaction withdrawalTransaction = new Transaction(Transaction.TransactionType.WITHDRAWAL, amount, fromAccount);
                withdrawalTransaction.setTransactionAmount(amount);
                withdrawalTransaction.setAccount(fromAccount);
                withdrawalTransaction.setTransactionDate(LocalDate.now());
                withdrawalTransaction.setTransactionType(Transaction.TransactionType.WITHDRAWAL);

                Transaction depositTransaction = new Transaction(Transaction.TransactionType.DEPOSIT, amount, toAccount);
                depositTransaction.setTransactionAmount(amount);
                depositTransaction.setAccount(toAccount);
                depositTransaction.setTransactionDate(LocalDate.now());
                depositTransaction.setTransactionType(Transaction.TransactionType.DEPOSIT);

                // Check if there is enough money in the account
                if (fromAccount.getBalance().compareTo(amount) > 0) {
                    // Perform withdrawal from sender
                    fromAccount.withdraw(amount);
                    accountRepository.save(fromAccount);
                    transactionService.saveTransaction(withdrawalTransaction);
                    transactionService.saveTransaction(depositTransaction);
                    // Perform deposit to receiver
                    toAccount.deposit(amount);
                    accountRepository.save(toAccount);

                } else {
                    throw new InsufficientFundsException("Insufficient funds for withdrawal from account ID " + fromAccountId);
                }

            } else {
                // Handle account not found
                throw new IllegalArgumentException("Account ID for " + fromAccountId + " or " + toAccountId + " not found!");
            }
        } catch (InsufficientFundsException e) {
            // Handle insufficient funds exception
            throw e; // You may choose to rethrow the same exception or handle it differently
        } catch (IllegalArgumentException e) {
            // Handle other exceptions, e.g., account not found
            throw e; // You may choose to rethrow the same exception or handle it differently
        } catch (Exception e) {
            // Handle generic exceptions
            throw new RuntimeException("An error occurred during transfer", e);
        }

    }

    @Transactional
    public void deposit(Long accountId, BigDecimal amount) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isPresent() && transactionRepository != null) {
            Account account = optionalAccount.get();

            // Create a transaction record
            Transaction transaction = new Transaction();
            transaction.setTransactionAmount(amount);
            transaction.setAccount(account);
            transaction.setTransactionDate(LocalDate.now());
            transaction.setTransactionType(Transaction.TransactionType.DEPOSIT);

            // Save the transaction
            transactionService.saveTransaction(transaction);

            // Update the account balance
            account.deposit(amount);

            // Save the updated account
            accountRepository.save(account);
        } else {
            // Handle account not found
            throw new IllegalArgumentException("Account not found for ID: " + accountId);
        }
    }

    @Transactional
    public void withdraw(Long accountId, BigDecimal amount) throws InsufficientFundsException {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isPresent() && transactionRepository != null) {
            Account account = optionalAccount.get();

            // Create a transaction record
            Transaction transaction = new Transaction();
            transaction.setTransactionAmount(amount);
            transaction.setAccount(account);
            transaction.setTransactionDate(LocalDate.now());
            transaction.setTransactionType(Transaction.TransactionType.WITHDRAWAL);

            // Save the transaction
            transactionService.saveTransaction(transaction);

            if (account.getBalance().compareTo(amount) > 0) {
                // Update the account balance
                account.withdraw(amount);
                // Save the updated account
                accountRepository.save(account);
            } else {
                throw new InsufficientFundsException("Insufficient funds for withdrawal from account ID "
                        + accountId);
            }

        } else {
            // Handle account not found
            throw new IllegalArgumentException("Account not found for ID: " + accountId);
        }
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteAccountById(id);
    }

    public static class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String msg) {
            super(msg);
        }
    }
}
