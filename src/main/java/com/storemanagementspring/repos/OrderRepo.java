package com.storemanagementspring.repos;

import com.storemanagementspring.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {

    Optional<List<Order>> getOrderByOrderDate(LocalDate orderDate);
}
