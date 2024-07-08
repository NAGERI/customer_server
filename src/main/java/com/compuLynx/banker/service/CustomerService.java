package com.compuLynx.banker.service;

import com.compuLynx.banker.model.Customer;
import com.compuLynx.banker.repo.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    Logger log = LoggerFactory.getLogger(this.getClass());

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {

        return customerRepository.findById(id).orElseThrow(null);
    }

    public Customer addCustomer(Customer customer) {
        customer.setCustomer_pin(generateCustomerPIN());
        try {
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            // Handle the case where the account ID already exists
            // You can log an error, throw a specific exception, or take appropriate action
            throw new IllegalArgumentException("Customer with ID " + customer.getCustomer_id() + " already exists.");
        }

    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    public Optional<Customer> findByCustomerId(String customer_id){
        Customer cus = customerRepository.findByCustomer_id(customer_id);
//        cus.setCustomer_pin(cus.getCustomer_pin());
        log.info("Password : {}", cus.getCustomer_pin());
        return Optional.of(cus);
    };
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
    private String generateCustomerPIN(){
        return "CPIN"+ System.currentTimeMillis();
    }
}
