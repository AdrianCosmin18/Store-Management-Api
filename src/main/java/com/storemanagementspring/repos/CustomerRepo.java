package com.storemanagementspring.repos;

import com.storemanagementspring.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Optional<Customer> getCustomersByEmail(String email);
    Optional<Customer> getCustomersByPhone(String phone);
}
