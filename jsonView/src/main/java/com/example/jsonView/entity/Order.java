package com.example.jsonView.entity;

import com.example.jsonView.dto.order.OrderSummary;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;


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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
