package com.storemanagementspring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Entity(name = "Product")
@ToString
public class Product {//one to many cu OrderDetails

    @Id
    @SequenceGenerator(name = "product_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    private Long id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "description")
    private String description;

    public Product(String brand, String name, Double price, Integer stock, String description) {
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    Set<OrderDetails> ordersDetails;

}
