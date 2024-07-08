package com.compuLynx.banker.repo;

import com.compuLynx.banker.model.Account;
import com.compuLynx.banker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount(Account account);

}
