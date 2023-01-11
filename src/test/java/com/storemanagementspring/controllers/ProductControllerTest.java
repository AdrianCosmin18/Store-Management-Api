package com.storemanagementspring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.TestUtils;
import com.storemanagementspring.dto.ProductDTO;
import com.storemanagementspring.models.Product;
import com.storemanagementspring.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Autowired
    private MockMvc restMockMVC;

    @InjectMocks
    private ProductController productController;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup(){

        restMockMVC = MockMvcBuilders.standaloneSetup(productController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
                .build();
    }


    @Test
    void shouldGetAllProducts() throws Exception{
        ModelMapper modelMapper = new ModelMapper();
        List<Product> productList = new ArrayList<>();
        ProductDTO legion5 = ProductDTO.builder().brand("Lenovo").name("Legion 5").stock(4).price(4399.6).description("512GB SSD, 16GB RAM, AMD Ryzen 7, GTX 1650").build();
        ProductDTO iPhone14 = ProductDTO.builder().brand("Apple").name("iPhone 14").stock(19).price(4650D).description("A15 bionic, 128 GB, color: black").build();
        ProductDTO tv = ProductDTO.builder().description("126 inch").price(1957.99).stock(3).name("TV - Series 7").brand("Samsung").build();

        productList.add(modelMapper.map(legion5, Product.class));
        productList.add(modelMapper.map(iPhone14, Product.class));
        productList.add(modelMapper.map(tv, Product.class));

        when(this.productService.getProducts()).thenReturn(productList);
        this.restMockMVC.perform(MockMvcRequestBuilders.get("/store-management/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(productList)));
    }

    @Test
    void shouldThrowExceptionGetAllProducts() throws Exception{
        when(this.productService.getProducts()).thenThrow(RuntimeException.class);
        this.restMockMVC.perform(MockMvcRequestBuilders.get("/store-management/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldGetProductById() throws Exception {
        Product product = Product.builder().id(1L).brand("Apple").name("iPhone 14").stock(20).price(12334D).description("sdfsdf").build();
        when(this.productService.getProductById(product.getId())).thenReturn(product);
        this.restMockMVC.perform(MockMvcRequestBuilders.get("/store-management/products/" + product.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(product)));
    }

    @Test
    void shouldThrowEceptionGetProductById() throws Exception {
        Product product = Product.builder().id(1L).brand("Apple").name("iPhone 14").stock(20).price(12334D).description("sdfsdf").build();
        when(this.productService.getProductById(product.getId())).thenThrow(RuntimeException.class);
        this.restMockMVC.perform(MockMvcRequestBuilders.get("/store-management/products/" + product.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

//    @Test
//    void shouldAddProduct(){
//
//        ProductDTO iPhone14 = ProductDTO.builder().brand("Apple").name("iPhone 14").stock(19).price(4650D).description("A15 bionic, 128 GB, color: black").build();
//        this.restMockMVC.perform(MockMvcRequestBuilders.get("/store-management/products")
//                .contentType(MediaType.APPLICATION_JSON))
//                .content()
//    }
}