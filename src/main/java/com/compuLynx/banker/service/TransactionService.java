package com.compuLynx.banker.service;

import com.compuLynx.banker.model.Account;
import com.compuLynx.banker.model.Transaction;
import com.compuLynx.banker.repo.TransactionRepository;
import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public List<Transaction> getTransactionsByAccount(Account account) {
        return transactionRepository.findByAccount(account);
    }

    public Transaction saveTransaction(Transaction transaction) {
        transaction.setTransaction_id(UuidCreator.getTimeOrdered());
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
