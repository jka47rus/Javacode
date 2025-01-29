package com.example.jsonView.entity;

import com.example.jsonView.dto.order.OrderSummary;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "`order`")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(OrderSummary.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonView(OrderSummary.class)
    private String product;

    @JsonView(OrderSummary.class)
    private double totalAmount;

    @JsonView(OrderSummary.class)
    private String status;

}
