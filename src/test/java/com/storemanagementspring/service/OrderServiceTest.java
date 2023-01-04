package com.storemanagementspring.service;

import com.storemanagementspring.dto.OrderDTO;
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
import static org.mockito.BDDMockito.then;
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

    @Test
    void shouldGetOrderById(){
        Order order = Order.builder().id(1L).orderAddress("Romania, Bucuresti").orderDate(LocalDate.parse("2022-12-12")).ammount(109.4).build();

        when(orderRepo.findById(order.getId())).thenReturn(Optional.of(order));
        assertThat(orderService.getOrderById(order.getId())).isEqualTo(order);
    }

    @Test
    void shouldThrowExceptionGetOrderById(){
        when(orderRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void shouldGetOrdersByDate(){
        List<Order> orders = new ArrayList<>();
        orders.add(Order.builder().orderAddress("Romania, Bucuresti").orderDate(LocalDate.parse("2022-12-12")).ammount(109.4).build());
        orders.add(Order.builder().orderAddress("Aleea Timisul de Jos 3").orderDate(LocalDate.parse("2022-12-12")).ammount(100D).build());

        when(orderRepo.getOrderByOrderDate(orders.get(0).getOrderDate())).thenReturn(Optional.of(orders));
        assertThat(orderService.getOrdersByDate(LocalDate.parse("2022-12-12"))).isEqualTo(orders);
    }

    @Test
    void shouldThrowExceptionGetOrdersByDate(){
        when(orderRepo.getOrderByOrderDate(LocalDate.parse("2022-12-12"))).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> orderService.getOrdersByDate(LocalDate.parse("2022-12-12")));
    }

    @Test
    void shouldThrowException2GetOrdersByDate(){
        when(orderRepo.getOrderByOrderDate(LocalDate.parse("2022-12-12"))).thenReturn(Optional.of(new ArrayList<>()));
        assertThrows(RuntimeException.class, () -> orderService.getOrdersByDate(LocalDate.parse("2022-12-12")));
    }

    @Test
    void shouldAddOrder(){
        OrderDTO orderDTO = OrderDTO.builder().orderAddress("Romania, Bucuresti").orderDate(LocalDate.parse("2022-12-12")).ammount(109.4).build();
        orderService.addOrder(orderDTO);
        then(orderRepo).should().save(orderArgumentCaptor.capture());
        assertThat(orderArgumentCaptor.getValue()).isEqualTo(new Order(orderDTO.getAmmount(), orderDTO.getOrderAddress(), orderDTO.getOrderDate()));
    }

    @Test
    void shouldDeleteById(){
        Order order = Order.builder().id(1L).orderAddress("Romania, Bucuresti").orderDate(LocalDate.parse("2022-12-12")).ammount(109.4).build();
        when(orderRepo.findById(order.getId())).thenReturn(Optional.of(order));
        orderService.deleteOrderById(order.getId());
        then(orderRepo).should().deleteById(order.getId());
    }

    @Test
    void shouldThrowExceptionDeleteById(){
        when(orderRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> orderService.deleteOrderById(1L));
    }

    @Test
    void shouldUpdateOrder(){
        Order order = Order.builder().id(1L).orderAddress("Romania, Bucuresti").orderDate(LocalDate.parse("2022-12-12")).ammount(109.4).build();
        OrderDTO orderDTO = OrderDTO.builder().orderAddress("Romania, Buzau").orderDate(LocalDate.parse("2022-12-13")).ammount(108D).build();

        when(orderRepo.findById(order.getId())).thenReturn(Optional.of(order));
        orderService.updateOrder(order.getId(), orderDTO);
        then(orderRepo).should().updateOrderById(order.getId(), orderDTO.getAmmount(), orderDTO.getOrderAddress(), orderDTO.getOrderDate());
    }

    @Test
    void shouldThrowExceptionUpdateOrder(){
        Order order = Order.builder().id(1L).orderAddress("Romania, Bucuresti").orderDate(LocalDate.parse("2022-12-12")).ammount(109.4).build();
        OrderDTO orderDTO = OrderDTO.builder().orderAddress("Romania, Buzau").orderDate(LocalDate.parse("2022-12-13")).ammount(108D).build();

        when(orderRepo.findById(order.getId())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> orderService.updateOrder(order.getId(), orderDTO));
    }
}