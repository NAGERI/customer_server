package com.compuLynx.banker.repo;

import com.compuLynx.banker.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    void deleteCustomerById(Long id);

    @Query("SELECT n FROM Customer n WHERE n.customer_id = ?1")
    Customer findByCustomer_id(String customerId);
}
