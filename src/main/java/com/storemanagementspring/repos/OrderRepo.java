package com.storemanagementspring.repos;

import com.storemanagementspring.models.Order;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {

    Optional<List<Order>> getOrderByOrderDate(LocalDate orderDate);

    @Query("update Order o set  o.ammount = ?2, o.orderAddress = ?3, o.orderDate = ?4 where o.id = ?1")
    void updateOrderById(Long id, Double amount, String orderAddress, LocalDate orderDate);
}
