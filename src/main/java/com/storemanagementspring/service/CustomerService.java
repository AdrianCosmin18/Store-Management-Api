package com.storemanagementspring.service;

import com.storemanagementspring.dto.CustomerDTO;
import com.storemanagementspring.dto.OrderDTO;
import com.storemanagementspring.dto.OrderDetailsDTO;
import com.storemanagementspring.models.Customer;
import com.storemanagementspring.models.Order;
import com.storemanagementspring.models.OrderDetails;
import com.storemanagementspring.models.Product;
import com.storemanagementspring.repos.CustomerRepo;
import com.storemanagementspring.repos.OrderDetailsRepo;
import com.storemanagementspring.repos.OrderRepo;
import com.storemanagementspring.repos.ProductRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private CustomerRepo customerRepo;
    private OrderRepo orderRepo;
    private ProductRepo productRepo;
    private OrderDetailsRepo orderDetailsRepo;

    public CustomerService(CustomerRepo customerRepo, OrderRepo orderRepo, ProductRepo productRepo, OrderDetailsRepo orderDetailsRepo) {
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.orderDetailsRepo = orderDetailsRepo;
    }

    public List<Customer> getCustomers(){
        List<Customer> customers = this.customerRepo.findAll();
        if(customers.size() > 0){
            return customers;
        }

        throw new RuntimeException("ERROR: Customers list is empty");
    }

    public Customer getCustomerById(Long id){

        Customer customer = customerRepo.findById(id).
                orElseThrow(() -> new RuntimeException("ERROR: There is no customer with this id: " + id));
        return customer;
    }

    public void addCustomer(CustomerDTO customerDTO){

        if(customerRepo.getCustomersByEmail(customerDTO.getEmail()).isPresent()){
            throw new RuntimeException("ERROR: already exists a customer with this email: " + customerDTO.getEmail());
        } else if (customerRepo.getCustomersByPhone(customerDTO.getPhone()).isPresent()) {
            throw new RuntimeException("ERROR: already exists a customer with this phone: " + customerDTO.getEmail());
        }
        customerRepo.save(new Customer(customerDTO.getFullName(), customerDTO.getEmail(), customerDTO.getPassword(), customerDTO.getRole(), customerDTO.getPhone(), customerDTO.getAddress()));
    }

    public void deleteCustomerById(Long id){

        if (!customerRepo.findById(id).isPresent()){
            throw new RuntimeException("ERROR: there is no customer with this id: " + id);
        }

        customerRepo.deleteById(id);
    }

    public void updateCustomerById(Long id, CustomerDTO customerDTO){

        if(customerRepo.findById(id).isEmpty()){
            throw new RuntimeException("ERROR: There is no customer with this id: " + id);
        }else if(customerRepo.getCustomersByEmail(customerDTO.getEmail()).isPresent()){
            throw new RuntimeException("ERROR: already exists a customer with this email: " + customerDTO.getEmail());
        } else if (customerRepo.getCustomersByPhone(customerDTO.getPhone()).isPresent()) {
            throw new RuntimeException("ERROR: already exists a customer with this phone: " + customerDTO.getEmail());
        }

        customerRepo.updateCustomerById(id, customerDTO.getFullName(), customerDTO.getEmail(), customerDTO.getPassword(), customerDTO.getRole(), customerDTO.getPhone());
    }

    public void placeOrder(Long customerId, Map<Long, Integer> pairProduct_Quantity, String address){

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("ERROR: There is no customer with this id"+customerId));

        Set<OrderDetailsDTO> orderDetailsSet = new HashSet<>();
        OrderDTO currentOrder = new OrderDTO();
        //consideram ca map-ul nu vine gol din front end

        //creem lista de orderDetails
        for(Map.Entry<Long, Integer> entry : pairProduct_Quantity.entrySet()){

            Product product = productRepo.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("ERROR: There is no product with this id:" + entry.getKey()));

            OrderDetailsDTO orderDetails = new OrderDetailsDTO();
            orderDetails.setQuantity(entry.getValue());
            orderDetails.setPrice(product.getPrice() * entry.getValue());

            orderDetailsSet.add(orderDetails);
        }

        //daca s-a mers fara nicio eroare
        currentOrder.setOrderDate(LocalDate.now());
        currentOrder.setOrderAddress(address);
        currentOrder.setAmmount(orderDetailsSet.stream().map(OrderDetailsDTO::getPrice).collect(Collectors.summingDouble(Double::doubleValue)));

        orderDetailsSet.forEach(o -> {
            OrderDetails orderDetails = new OrderDetails(o.getPrice(), o.getQuantity());

        });
    }

}
















