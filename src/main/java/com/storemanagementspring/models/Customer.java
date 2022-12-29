package com.storemanagementspring.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "Customer")
@Table(name = "customer")
@Builder
public class Customer {//one to many cu Order

    @Id
    @SequenceGenerator(name = "customer_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    private Long id;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "phone", nullable = false) //de ce nu pot folosi unique, eroare ca ar fi cheia prea lunga
    private String phone;

    public Customer(String fullName, String email, String password, String role, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
    }

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();
}
