package com.storemanagementspring.repos;

import com.storemanagementspring.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {

    Optional<List<OrderDetails>> getOrderDetailsByQuantity(Integer quantity);
}
