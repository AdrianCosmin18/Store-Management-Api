package com.storemanagementspring.repos;

import com.storemanagementspring.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {
}
