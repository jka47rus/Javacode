package com.example.MVCObject.mapper.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity(name = "`order`")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;

    private Date orderDate;

    @NotBlank(message = "Адрес доставки обязателен")
    private String shippingAddress;

    private double totalPrice;

    private String orderStatus;

}
