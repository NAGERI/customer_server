package com.compuLynx.banker.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "account")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "customer")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cus_acc_id")
    private Customer customer;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "opening_date")
    private LocalDate openingDate;

    @Column(name = "balance")
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
    }

    public Account(Long id, Customer customer, String accountNumber, LocalDate openingDate, BigDecimal balance, List<Transaction> transactions) {
        this.id = id;
        this.customer = customer;
        this.accountNumber = accountNumber;
        this.openingDate = openingDate;
        this.balance = balance;
        this.transactions = transactions;
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {

        return "Account{" +
                "id=" + id +
                ", customer=" + customer +
                ", accountNumber='" + accountNumber + '\'' +
                ", openingDate='" + openingDate + '\'' +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';

    }
}
