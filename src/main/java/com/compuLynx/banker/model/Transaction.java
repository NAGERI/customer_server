package com.compuLynx.banker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "transaction")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "account")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "transaction_id", updatable = false, nullable = false)
    private UUID transaction_id;
    @ManyToOne
    @JoinColumn(name = "acc_trans_id")
    private Account account;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    public Transaction(TransactionType transactionType, BigDecimal amount, Account fromAccount) {
        this.transactionType = transactionType;
        this.transactionAmount = amount;
        account = fromAccount;
    }


    public Transaction() {
    }

    public Transaction(UUID transaction_id, Long id, TransactionType transactionType, Account account, BigDecimal transactionAmount, LocalDate transactionDate) {
        this.id = id;
        this.transaction_id = transaction_id;
        this.account = account;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTransaction_id(UUID transaction_id) {
        this.transaction_id = transaction_id;
    }

    public UUID getTransaction_id() {
        return transaction_id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transaction_id=" + transaction_id +
                ", account=" + account +
                ", transactionType=" + transactionType +
                ", transactionAmount=" + transactionAmount +
                ", transactionDate=" + transactionDate +
                '}';
    }

    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }
}
