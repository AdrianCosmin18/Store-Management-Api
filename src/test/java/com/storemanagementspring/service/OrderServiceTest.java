package com.storemanagementspring.service;

import com.storemanagementspring.models.Order;
import com.storemanagementspring.models.Product;
import com.storemanagementspring.repos.OrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @InjectMocks
    private OrderService orderService;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetOrders(){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.builder().orderAddress("Romania, Bucuresti").orderDate(LocalDate.parse("2022-12-12")).ammount(109.4).build());
        orders.add(Order.builder().orderAddress("Aleea Timisul de Jos 3").orderDate(LocalDate.parse("2023-01-01")).ammount(100D).build());

        when(orderRepo.findAll()).thenReturn(orders);
        assertThat(orderService.getOrders().size()).isEqualTo(2);
    }

    @Test
    void shouldThrowExceptionGetOrders(){
        when(orderRepo.findAll()).thenReturn(new ArrayList<>());
        assertThrows(RuntimeException.class, () -> orderService.getOrders());
    }
}