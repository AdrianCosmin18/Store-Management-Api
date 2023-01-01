package com.storemanagementspring.repos;

import com.storemanagementspring.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Optional<Customer> getCustomersByEmail(String email);
    Optional<Customer> getCustomersByPhone(String phone);

    @Query(value = "update Customer c set c.fullName = ?2, c.email = ?3, c.password = ?4, c.role = ?5, c.phone = ?6 where c.id = ?1")
    void updateCustomerById(Long id, String fullName, String email, String password, String role, String phone);

}
