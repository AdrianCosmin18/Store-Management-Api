package com.storemanagementspring.repos;

import com.storemanagementspring.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<List<Product>> getProductsByBrand(String brand);

    Optional<Product> getProductsByBrandAndAndName(String brand, String name);

    @Query("update Product p set p.brand = ?2, p.name = ?3, p.price = ?4, p.stock = ?5, p.description = ?6 where p.id = ?1")
    void updateProductById(Long id, String brand, String name, Double price, Integer stock, String description);
}
