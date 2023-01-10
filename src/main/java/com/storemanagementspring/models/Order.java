package com.storemanagementspring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Order")
@Table(name = "orders")
public class Order {

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    Set<OrderDetails> ordersDetailsSet;

    public void addOrderDetails(OrderDetails orderDetails){
        this.ordersDetailsSet.add(orderDetails);
    }

    public void deleteOrderDetails(OrderDetails orderDetails){
        this.ordersDetailsSet.remove(orderDetails);
    }
}
