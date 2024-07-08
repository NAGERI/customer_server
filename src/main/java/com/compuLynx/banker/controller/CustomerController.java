package com.compuLynx.banker.controller;


import com.compuLynx.banker.model.Account;
import com.compuLynx.banker.model.Customer;
import com.compuLynx.banker.model.EntityResponse;
import com.compuLynx.banker.security.UserPrincipal;
import com.compuLynx.banker.service.AccountService;
import com.compuLynx.banker.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/customers")
@Tag(name = "Customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    Logger logRes = LoggerFactory.getLogger("request-logs");
    Logger logReq = LoggerFactory.getLogger("response-logs");


    @Operation(summary = "This method is used to get all customer records.")
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        logger.info("Getting all the Customer records");
        logReq.info("Getting all the customer records");
        List<Customer> customers = customerService.getAllCustomers();
        logRes.info("Customers fetched   {}", customers.stream().count());
        return  new ResponseEntity<>(customers,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "This method is used to get a specific customer record with given {id}.")
    public ResponseEntity<Customer> getCustomerById( @PathVariable Long id) {

        final Customer customer = customerService.getCustomerById(id);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<>(customer,HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = "This method is used to create a customer record.")
    public <T> ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {

        try {
            Customer newCustomer = customerService.addCustomer(customer);
            Account acc = new Account();
            acc.setCustomer(newCustomer);
            acc.setBalance(BigDecimal.valueOf(0));
            acc.setAccountNumber(generateAccountNumber());
            acc.setOpeningDate(LocalDate.now());
            accountService.saveAccount(acc);
            logReq.info("save Customer details to the database : {} ", newCustomer.toString());

            return new ResponseEntity<>( newCustomer,HttpStatus.CREATED);
        }   catch(DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update/{id}")
    @Operation(summary = "This method is used to update a customer record.")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer != null) {
            customer.setId(id);
            Customer updatedCustomer = customerService.addCustomer(customer);
            return new ResponseEntity<>(updatedCustomer,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Handle not found case
        }
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "This method is used to delete a customer record.")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        logReq.info("Deleting one Customer record of id={}",id);
        customerService.deleteCustomer(id);
        logRes.info("Deleted Customer of id={}",id );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String generateAccountNumber() {
        // Your logic to generate a unique account number
        // This could involve a combination of a prefix, timestamp, random numbers, etc.
        return "ACC" + System.currentTimeMillis();
    }

}