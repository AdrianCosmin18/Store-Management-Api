package com.storemanagementspring.service;

import com.storemanagementspring.dto.CustomerDTO;
import com.storemanagementspring.models.Customer;
import com.storemanagementspring.repos.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private CustomerService customerService;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldGetCustomers(){
        List<Customer> customers = new ArrayList<>();
        customers.add(Customer.builder().email("cosmin_ndlc@yahoo.com").fullName("Nedelcu Adrian Cosmin").phone("0773941106").role("admin").build());
        customers.add(Customer.builder().email("annemdm@yahoo.com").fullName("Draghici Anne-Marie").phone("0769693767").role("user").build());

        Mockito.when(customerRepo.findAll()).thenReturn(customers);
        assertThat(customerService.getCustomers()).isEqualTo(customers);
    }

    @Test
    void shouldThrowExceptionGetCustomers(){
        List<Customer> customers = new ArrayList<>();

        Mockito.when(customerRepo.findAll()).thenReturn(customers);
        assertThrows(RuntimeException.class, () -> customerService.getCustomers());
    }

    @Test
    void shouldGetCustomerById(){
        Customer customer = Customer.builder().id(1L).email("cosmin_ndlc@yahoo.com").fullName("Nedelcu Adrian Cosmin").phone("0773941106").role("admin").build();

        Mockito.when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
        assertThat(customerService.getCustomerById(1L)).isEqualTo(customer);
    }

    @Test
    void shouldThrowExceptionGetCustomerById(){
        Mockito.when(customerRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> customerService.getCustomerById(1L));
    }

    @Test
    void shouldAddCustomer(){

        CustomerDTO customerDTO = CustomerDTO.builder().email("cosmin_ndlc@yahoo.com").fullName("Nedelcu Adrian Cosmin").phone("0773941106").role("admin").build();

        Mockito.when(customerRepo.getCustomersByEmail(customerDTO.getEmail())).thenReturn(Optional.empty());
        Mockito.when(customerRepo.getCustomersByEmail(customerDTO.getEmail())).thenReturn(Optional.empty());
        customerService.addCustomer(customerDTO);
        then(customerRepo).should().save(customerArgumentCaptor.capture());
        assertThat(customerArgumentCaptor.getValue()).isEqualTo(new Customer(customerDTO.getFullName(), customerDTO.getEmail(), customerDTO.getPassword(), customerDTO.getRole(), customerDTO.getPhone()));
    }

    @Test
    void shouldThrowException1AddCustomer(){
        Customer customer = Customer.builder().email("cosmin_ndlc@yahoo.com").phone("0773941106").build();
        CustomerDTO customerDTO = CustomerDTO.builder().email("cosmin_ndlc@yahoo.com").fullName("Nedelcu Adrian Cosmin").phone("0773941106").role("admin").build();

        Mockito.when(customerRepo.getCustomersByPhone(customer.getPhone())).thenReturn(Optional.of(customer));
        assertThrows(RuntimeException.class, () -> customerService.addCustomer(customerDTO));
    }

    @Test
    void shouldThrowException2AddCustomer(){
        Customer customer = Customer.builder().email("cosmin_ndlc@yahoo.com").phone("0773941106").build();
        CustomerDTO customerDTO = CustomerDTO.builder().email("cosmin_ndlc@yahoo.com").fullName("Nedelcu Adrian Cosmin").phone("0773941106").role("admin").build();

        Mockito.when(customerRepo.getCustomersByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        assertThrows(RuntimeException.class, () -> customerService.addCustomer(customerDTO));

    }
}