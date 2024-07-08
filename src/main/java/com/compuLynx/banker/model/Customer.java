package com.compuLynx.banker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Customer Entity:
 * @Fields id - auto generated, name, email, customer_id, customer_pin
 *
 */
@Entity
@Table( name = "customer")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String customer_id;
    @Column(nullable = false, unique = true)
    private String customer_pin;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Account> accounts;

    public Customer() {
    }

    public Customer(Long id, String name,String customer_id, String email, String customer_pin, List<Account> accounts) {
        this.id = id;
        this.customer_pin = customer_pin;
        this.name = name;
        this.email = email;
        this.customer_id = customer_id;
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_pin() {
        return customer_pin;
    }

    public void setCustomer_pin(String customer_pin) {
        this.customer_pin = customer_pin;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", customer_pin='" + customer_pin + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
