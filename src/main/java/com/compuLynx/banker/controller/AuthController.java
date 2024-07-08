package com.compuLynx.banker.controller;

import com.compuLynx.banker.model.*;
import com.compuLynx.banker.service.AuthService;
import com.compuLynx.banker.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@Tag(name = "Authorization/Authentication")
public class AuthController {

    @Autowired
    private final AuthService authService;
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerService customerService;


    Logger logRes = LoggerFactory.getLogger("request-logs");
    Logger logReq = LoggerFactory.getLogger("response-logs");

    @PostMapping("/api/auth/login")
    @Operation(summary = "This method is used to authenticate a customer.")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        logReq.info("Log In Credentials customer_id={} , customer_pin={}", request.getCustomer_id(),
                request.getCustomer_pin());
//        return authService.attemptLogin(request.getCustomer_id(), request.getCustomer_pin());
        var cus = customerService.findByCustomerId(request.getCustomer_id()).orElseThrow();

        if (cus.getCustomer_pin().equals(request.getCustomer_pin())) {
            logReq.info("Customer Logged In Successfully id={} name={} pin={}",cus.getCustomer_id(), cus.getName(),
                    cus.getCustomer_pin());
            return LoginResponse.builder().token(cus.getId().toString()).build();
        }
        return LoginResponse.builder().token(null).build();
    }

    @PostMapping("/api/auth/register")
    @Operation(summary = "This method is used to register a customer and create an account.")
    public ResponseEntity<Customer> register(@RequestBody RegisterRequest request) {
        logReq.info("Registering Customer :- customer_id={} name={} email={}", request.getCustomer_id(), request.getName(), request.getEmail());

        Customer customer = new Customer();
        customer.setCustomer_id(request.getCustomer_id());
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());

        return customerController.createCustomer(customer);
    }

}
