package com.storemanagementspring.service;

import com.storemanagementspring.dto.OrderDTO;
import com.storemanagementspring.models.Order;
import com.storemanagementspring.repos.OrderRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<Order> getOrders(){
        List<Order> orders = orderRepo.findAll();
        if (!orders.isEmpty()){
            return orders;
        }
        throw new RuntimeException("ERROR: Orders list is empty");
    }

    public Order getOrderById(Long id){

        return orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ERROR: There is no product with this id: " + id));
    }

    public List<Order> getOrdersByDate(LocalDate orderDate){
        List<Order> orders = orderRepo.getOrderByOrderDate(orderDate)
                .orElseThrow(() -> new RuntimeException("ERROR: eroare la lista"));
        if (orders.isEmpty()){
            throw new RuntimeException("ERROR: There is no order made on this day: " + orderDate);
        }
        return orders;
    }

    public void addOrder(OrderDTO orderDTO){
        try{
            orderRepo.save(new Order(orderDTO.getAmmount(), orderDTO.getOrderAddress(), orderDTO.getOrderDate()));

        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }

    public void deleteOrderById(Long id){
        if (orderRepo.findById(id).isEmpty()){
            throw new RuntimeException("ERROR: There is no order with this id: " + id);
        }
        orderRepo.deleteById(id);
    }

    public void updateOrder(Long id, OrderDTO orderDTO){
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ERROR: There is no order with this id : "+ id));
        orderRepo.updateOrderById(id, orderDTO.getAmmount(), orderDTO.getOrderAddress(), orderDTO.getOrderDate());
    }
}
