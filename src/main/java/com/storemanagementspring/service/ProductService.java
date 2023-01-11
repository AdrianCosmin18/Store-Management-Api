package com.storemanagementspring.service;

import com.storemanagementspring.dto.ProductDTO;
import com.storemanagementspring.models.OrderDetails;
import com.storemanagementspring.models.Product;
import com.storemanagementspring.repos.OrderDetailsRepo;
import com.storemanagementspring.repos.ProductRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    private ProductRepo productRepo;
    private OrderDetailsRepo orderDetailsRepo;

    public ProductService(ProductRepo productRepo, OrderDetailsRepo orderDetailsRepo) {
        this.productRepo = productRepo;
        this.orderDetailsRepo = orderDetailsRepo;
    }

    public List<Product> getProducts(){
        List<Product> products = productRepo.findAll();
        if(!products.isEmpty()){
            return products;
        }

        throw new RuntimeException("ERROR: Products list is empty");
    }

    public Product getProductById(Long id){

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ERROR: There is no product with this id: " + id));
        return product;
    }

    public List<Product> getProductsByBrand(String brand){
        List<Product> products = productRepo.getProductsByBrand(brand)
                .orElseThrow(() -> new RuntimeException("ERROR: There is no product with this brand: " + brand));
        return products;
    }

    @Modifying
    public void addProduct(ProductDTO productDTO){

        if(productRepo.getProductsByBrandAndAndName(productDTO.getBrand(), productDTO.getName()).isPresent()){
            throw new RuntimeException("ERROR: There is already a product with the same brand and name");
        }
        productRepo.save(new Product(productDTO.getBrand(), productDTO.getName(), productDTO.getPrice(), productDTO.getStock(), productDTO.getDescription()));
    }

    public void deleteProductById(Long id){

        if (productRepo.findById(id).isEmpty()){
            throw new RuntimeException("ERROR: There is no product with this id: " + id);
        }
        productRepo.deleteById(id);
    }

    public void updateProductById(Long id, ProductDTO productDTO){

        Product product = productRepo.findById(id).orElseThrow(() -> new RuntimeException("ERROR: There is no product with this id: " + id));
        if(productRepo.getProductsByBrandAndAndName(productDTO.getBrand(), productDTO.getName()).isPresent()){
            throw new RuntimeException("ERROR: There is already a product with the same brand and name");
        }
        productRepo.updateProductById(id, productDTO.getBrand(), productDTO.getName(), productDTO.getPrice(), productDTO.getStock(), productDTO.getDescription());
    }

    public List<Product> getBestSoldProducts(){

        List<OrderDetails> orderDetailsList = orderDetailsRepo.findAll();
        if(orderDetailsList.size() == 0){
            throw new RuntimeException("ERROR: There is no product sold yet");
        }
        Map<Product, Integer> map = new HashMap<>();

        for(OrderDetails orderDetails: orderDetailsList){

            Product product = orderDetails.getProduct();

            if(map.containsKey(orderDetails.getProduct())){
                map.put(product, map.get(product) + orderDetails.getQuantity());
            }else{
                map.put(product, orderDetails.getQuantity());
            }
        }

        Collection<Integer> quantities = map.values();
        int maxQuantity = Collections.max(quantities);
        List<Product> productList = new ArrayList<>();
        System.out.println("quantities maxim:" + maxQuantity);

        for (Map.Entry<Product, Integer> entry : map.entrySet()) {
            if (entry.getValue() == maxQuantity) {
                productList.add(entry.getKey());
            }
        }
        System.out.println(productList);
        return productList;
    }

    //nu stiu daca merge
    public List<Product> getBestSoldProductsWithQuery(){
        List<Product> products = productRepo.findMostSoldProducts();
        if (products.isEmpty()){
            throw new RuntimeException("ERROR: There is no product sold yet");
        }
        System.out.println(products);
        return products;
    }
}
