package com.compuLynx.banker.repo;

import com.compuLynx.banker.model.Account;
import com.compuLynx.banker.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findCustomerById(Customer customer);

    List<Account> findByCustomer(Customer customer);

    void deleteAccountById(Long id);
}
