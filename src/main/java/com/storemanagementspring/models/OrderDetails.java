package com.storemanagementspring.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderDetails")
@Entity(name = "OrderDetails")
@ToString
public class OrderDetails {

    @Id
    @SequenceGenerator(name = "orderDetails_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderDetails_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id"
            , referencedColumnName = "id"
            , foreignKey = @ForeignKey(name = "product_id_fk"))
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id"
            , referencedColumnName = "id"
            , foreignKey = @ForeignKey(name = "order_id_fk"))
    private Order order;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public OrderDetails(Double price, Integer quantity) {
        this.price = price;
        this.quantity = quantity;
    }
}
