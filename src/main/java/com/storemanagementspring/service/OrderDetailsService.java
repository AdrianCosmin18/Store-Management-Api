package com.storemanagementspring.service;

import com.storemanagementspring.dto.OrderDetailsDTO;
import com.storemanagementspring.models.OrderDetails;
import com.storemanagementspring.repos.OrderDetailsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {

    private OrderDetailsRepo orderDetailsRepo;

    public OrderDetailsService(OrderDetailsRepo orderDetailsRepo) {
        this.orderDetailsRepo = orderDetailsRepo;
    }

    public OrderDetails getOrderDetailById(Long id){
        return orderDetailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ERROR: There is no order detail with this id: " + id));
    }

    public List<OrderDetails> getOrderDetailsByQuantity(Integer quantity){
        List<OrderDetails> orderDetailsList = orderDetailsRepo.getOrderDetailsByQuantity(quantity)
                .orElseThrow(() -> new RuntimeException("ERROR: Eroare la crearea listei"));
        if (orderDetailsList.isEmpty()){
            throw new RuntimeException("ERROR: There is no order detail with this quantity: " + quantity);
        }
        return orderDetailsList;
    }

}
