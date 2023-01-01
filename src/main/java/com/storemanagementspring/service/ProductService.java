package com.storemanagementspring.service;

import com.storemanagementspring.dto.ProductDTO;
import com.storemanagementspring.models.Product;
import com.storemanagementspring.repos.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
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
}
