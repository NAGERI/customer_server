package com.compuLynx.banker.controller;

import com.compuLynx.banker.model.Account;
import com.compuLynx.banker.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts")
public class AccountController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    Logger logRes = LoggerFactory.getLogger("request-logs");
    Logger logReq = LoggerFactory.getLogger("response-logs");
    @Autowired
    private AccountService accountService;

    @GetMapping
    @Operation(summary = "This method is used to get all account records.")
    public ResponseEntity<List<Account>> getAllAccounts() {
        logger.info("Getting all the Account records");
        logReq.info("Getting all the Account records");
        List<Account> account = accountService.getAllAccounts();
        if (!account.isEmpty()) {
            logRes.info("Accounts {}", account.size());
        } else {
            logRes.info("No accounts available!");
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "This method is used to get an account by {id}.")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        logReq.info("Getting an the Account by id={}", id);
        Account acc = accountService.getAccountById(id);
        logRes.info("Account by id={}", id);
        return new ResponseEntity<>(acc, HttpStatus.OK);
    }

    @GetMapping("/balance/{id}")
    @Operation(summary = "This method is used to get balance of account {id}.")
    public ResponseEntity<BigDecimal> getCustomerBalance(@PathVariable Long id) {
        logReq.info("Getting Balance of account id={}", id);
        Account acc = accountService.getAccountById(id);
        logRes.info("Balance of account id={}", id);
        return new ResponseEntity<>(acc.getBalance(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "This method is used to create an account record.")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {

        account.setAccountNumber(generateAccountNumber());
        logReq.info("save Account details to the database : {} ", account);
        Account acc = accountService.saveAccount(account);
        logRes.info("saved new account details to the database => {} ", acc);
        return new ResponseEntity<>(acc, HttpStatus.CREATED);
    }


    @PutMapping("update/{id}")
    @Operation(summary = "This method is used to update a account by {id}.")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        Account existingAccount = accountService.getAccountById(id);
        logReq.info("Updating one Account record of id={} =>  {}", id, existingAccount);
        if (existingAccount != null) {
            account.setId(id);
            logRes.info("Updated account of id={} => {}", id, account);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "This method is used to delete an account by {id}.")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        logReq.info("Deleting one Account record of id={}", id);
        accountService.deleteAccount(id);
        logRes.info("Deleted account  of id={}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{accountId}/deposit")
    @Operation(summary = "This method is used to delete an account by {id}.")
    public ResponseEntity<String> deposit(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        logReq.info("Initiate Deposit to account_id={} of amount={}", accountId,amount);
        accountService.deposit(accountId, amount);
        logRes.info("Deposited money to account_id={} of amount={}", accountId,amount);
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/{accountId}/withdraw")
    @Operation(summary = "This method is used to delete an account by {id}.")
    public ResponseEntity<String> withdraw(@PathVariable Long accountId, @RequestParam BigDecimal amount) throws AccountService.InsufficientFundsException {

        try {
            logReq.info("Initiating withdraw of money from account_id={} of amount={}", accountId,amount);
            accountService.withdraw(accountId, amount);
            logRes.info("Withdrawn money from account_id={} of amount={}", accountId,amount);
            return ResponseEntity.ok("Withdrawal successful");
        } catch (AccountService.InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Funds!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }

    }

    @PostMapping("/transfer/{amount}")
    @Operation(summary = "This method is used to transfer money from AccountA to AccountB.")
    public ResponseEntity<String> transfer(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @PathVariable BigDecimal amount) throws AccountService.InsufficientFundsException {
        logReq.info("Initiating transfer of money from {} to {} of amount={}", fromAccountId,toAccountId,amount);
        accountService.transfer(fromAccountId, toAccountId, amount);
        logRes.info("Transferred money from {} to {} of amount={}", fromAccountId,toAccountId,amount);

        return ResponseEntity.ok("Transfer successful");
    }
}