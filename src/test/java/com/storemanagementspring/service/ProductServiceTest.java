package com.storemanagementspring.service;

import com.storemanagementspring.dto.ProductDTO;
import com.storemanagementspring.models.Customer;
import com.storemanagementspring.models.Product;
import com.storemanagementspring.repos.CustomerRepo;
import com.storemanagementspring.repos.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldGetProducts(){
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().brand("apple").name("iPhone 14").description("A15 cip").stock(113).build());
        products.add(Product.builder().brand("lenovo").name("Legion 5").description("15.4 inch, AMD Ryzen 7, 2021").stock(3).build());

        when(productRepo.findAll()).thenReturn(products);
        assertThat(productService.getProducts().size()).isEqualTo(products.size());
    }

    @Test
    void shouldThrowExceptionGetProducts(){
        when(productRepo.findAll()).thenReturn(new ArrayList<>());
        assertThrows(RuntimeException.class, () -> productService.getProducts());
    }

    @Test
    void shouldGetProductById(){

        Product iPhone14 = Product.builder().id(1L).brand("apple").name("iPhone 14").description("A15 cip").stock(113).build();
        when(productRepo.findById(iPhone14.getId())).thenReturn(Optional.of(iPhone14));
        assertThat(productService.getProductById(1L)).isEqualTo(iPhone14);
    }

    @Test
    void shouldThrowExceptionGetProductById(){

        Product iPhone14 = Product.builder().id(1L).brand("apple").name("iPhone 14").description("A15 cip").stock(113).build();
        when(productRepo.findById(iPhone14.getId())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
    }

    @Test
    void shouldGetProductsByBrand(){
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().brand("apple").name("iPhone 14").description("A15 cip").stock(113).build());
        products.add(Product.builder().brand("apple").name("iPhone 14 Plus").description("A15 cip").stock(54).build());

        when(productRepo.getProductsByBrand("apple")).thenReturn(Optional.of(products));
        assertThat(productService.getProductsByBrand("apple").get(0).getName()).isEqualTo("iPhone 14");
    }

    @Test
    void shouldThrowExceptionGetProductsByBrand(){
        when(productRepo.getProductsByBrand("apple")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productService.getProductsByBrand("apple"));
    }

    @Test
    void shouldAddProduct(){

        ProductDTO iPhone14 = ProductDTO.builder().brand("apple").name("iPhone 14").description("A15 cip").stock(113).build();

        when(productRepo.getProductsByBrandAndAndName(iPhone14.getBrand(), iPhone14.getName())).thenReturn(Optional.empty());
        productService.addProduct(iPhone14);
        then(productRepo).should().save(productArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue()).isEqualTo(new Product(iPhone14.getBrand(), iPhone14.getName(), iPhone14.getPrice(), iPhone14.getStock(), iPhone14.getDescription()));
    }

    @Test
    void shouldThrowExceptionAddProduct(){
        ProductDTO iPhone14Dto = ProductDTO.builder().brand("apple").name("iPhone 14").description("A15 cip").stock(113).build();
        Product iPhone14 = Product.builder().id(1L).brand("apple").name("iPhone 14").description("A15 cip").stock(113).build();

        when(productRepo.getProductsByBrandAndAndName(iPhone14Dto.getBrand(), iPhone14Dto.getName())).thenReturn(Optional.of(iPhone14));
        assertThrows(RuntimeException.class, () -> productService.addProduct(iPhone14Dto));
    }

    @Test
    void shouldDeleteProductById(){

        Product iPhone14 = Product.builder().id(1L).brand("apple").name("iPhone 14").description("A15 cip").stock(113).build();

        when(productRepo.findById(1L)).thenReturn(Optional.of(iPhone14));
        productService.deleteProductById(iPhone14.getId());
        then(productRepo).should().deleteById(iPhone14.getId());
    }

    @Test
    void shouldThrowExceptionDeleteProductById(){

        Product iPhone14 = Product.builder().id(1L).brand("apple").name("iPhone 14").description("A15 cip").stock(113).build();

        when(productRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productService.deleteProductById(iPhone14.getId()));
    }

    @Test
    void shouldUpdateProductById(){
        ProductDTO iPhone14Dto = ProductDTO.builder().brand("apple").name("iPhone 14").description("A15 chip").stock(113).build();
        Product iPhone14 = Product.builder().id(1L).brand("apple").name("iPhone 14").description("A15 chip, 128 GB").stock(109).build();

        when(productRepo.findById(iPhone14.getId())).thenReturn(Optional.of(iPhone14));
        when(productRepo.getProductsByBrandAndAndName(iPhone14Dto.getBrand(), iPhone14Dto.getName())).thenReturn(Optional.empty());
        productService.updateProductById(1L, iPhone14Dto);
        then(productRepo).should().updateProductById(1L, iPhone14Dto.getBrand(), iPhone14Dto.getName(), iPhone14Dto.getPrice(), iPhone14Dto.getStock(), iPhone14Dto.getDescription());
    }

    @Test
    void shouldThrowExceptionUpdateProductById(){
        ProductDTO iPhone14Dto = ProductDTO.builder().brand("apple").name("iPhone 14").description("A15 chip").stock(113).build();
        Product iPhone14 = Product.builder().id(1L).brand("apple").name("iPhone 14").description("A15 chip, 128 GB").stock(109).build();

        when(productRepo.findById(iPhone14.getId())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productService.updateProductById(iPhone14.getId(), iPhone14Dto));
    }

    @Test
    void shouldThrowException2UpdateProductById(){
        ProductDTO iPhone14Dto = ProductDTO.builder().brand("apple").name("iPhone 14").description("A15 chip").stock(113).build();
        Product iPhone14 = Product.builder().id(1L).brand("apple").name("iPhone 14").description("A15 chip, 128 GB").stock(109).build();

        when(productRepo.findById(iPhone14.getId())).thenReturn(Optional.of(iPhone14));
        when(productRepo.getProductsByBrandAndAndName(iPhone14.getBrand(), iPhone14.getName())).thenReturn(Optional.of(iPhone14));
        assertThrows(RuntimeException.class, () -> productService.updateProductById(iPhone14.getId(), iPhone14Dto));
    }
}