package com.compuLynx.banker.controller;

import com.compuLynx.banker.model.EntityResponse;
import com.compuLynx.banker.model.Transaction;
import com.compuLynx.banker.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions")
public class TransactionController {


    @Autowired
    private final TransactionService transactionService;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    Logger logRes = LoggerFactory.getLogger("request-logs");
    Logger logReq = LoggerFactory.getLogger("response-logs");
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    @Operation(summary = "This method is used to retrieve all record.")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        logger.info("Getting all the Transaction records");
        logReq.info("Getting all the Transaction records");

        List<Transaction> trans = transactionService.getAllTransactions();

        logRes.info("Transactions {}",trans.size() );
        return new ResponseEntity<> (trans,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "This method is used to get transaction by {id}.")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        logReq.info("Getting one Transaction record of id={}",id);
        Transaction trans = transactionService.getTransactionById(id);
        logRes.info("Transaction by id={} {}",id,trans);
        return new ResponseEntity<>(trans, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "This method is used to create a transaction record.")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        logReq.info("Creating one Transaction record  {}",transaction);

        Transaction trans = transactionService.saveTransaction(transaction);
        logRes.info("Created transaction  {}",trans );
        return new ResponseEntity<>(trans,HttpStatus.CREATED);
    }
    @PutMapping("update/{id}")
    @Operation(summary = "This method is used to update a transaction record.")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        Transaction existingTrans = transactionService.getTransactionById(id);
        logReq.info("Updating one Transaction record of id={} =>  {}",id,existingTrans);
        if (existingTrans != null) {
            transaction.setId(id);
            Transaction trans = transactionService.saveTransaction(transaction);
            logRes.info("Updated transaction of id={} => {}",id,trans );
            return new ResponseEntity<>(trans, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Handle not found case
        }
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "This method is used to delete a transaction record.")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        logReq.info("Deleting one Transaction record of id={}",id);
        transactionService.deleteTransaction(id);
        logRes.info("Deleted transaction  of id={}",id );
        return new ResponseEntity<>(HttpStatus.OK);
    }
}