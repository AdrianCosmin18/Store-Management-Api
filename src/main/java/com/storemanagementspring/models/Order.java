package com.storemanagementspring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
@Entity(name = "Order")
@ToString
public class Order {//Many to One cu Product

    @Id
    @SequenceGenerator(name = "order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    private Long id;

    @Column(name = "ammount", nullable = false)
    private Double ammount;

    @Column(name = "orderAddress", nullable = false)
    private String orderAddress;

    @Column(name = "orderDate", nullable = false)
    private LocalDate orderDate;

    public Order(Double ammount, String orderAddress, LocalDate orderDate) {
        this.ammount = ammount;
        this.orderAddress = orderAddress;
        this.orderDate = orderDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id"
            ,referencedColumnName = "id"
            ,foreignKey = @ForeignKey(name = "customer_id_fk"))
    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "order")
    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    Set<OrderDetails> ordersDetails;


}