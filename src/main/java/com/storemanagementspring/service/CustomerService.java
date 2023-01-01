package com.storemanagementspring.service;

import com.storemanagementspring.dto.CustomerDTO;
import com.storemanagementspring.models.Customer;
import com.storemanagementspring.repos.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo){
        this.customerRepo = customerRepo;
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
        customerRepo.save(new Customer(customerDTO.getFullName(), customerDTO.getEmail(), customerDTO.getPassword(), customerDTO.getRole(), customerDTO.getPhone()));
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
}
















