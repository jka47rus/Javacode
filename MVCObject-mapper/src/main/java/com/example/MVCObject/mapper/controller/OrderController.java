package com.example.MVCObject.mapper.controller;

import com.example.MVCObject.mapper.entity.Order;
import com.example.MVCObject.mapper.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;
    private ObjectMapper objectMapper;


    @GetMapping
    public List<?> getAllOrders() {
        return orderService.getAllOrders().stream().map(order -> {
                    try {
                        return objectMapper.writeValueAsString(order);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) throws JsonProcessingException {
        Order order = orderService.getOrderById(id);
        return order != null ? ResponseEntity.ok(objectMapper.writeValueAsString(order)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody String orderJson) {

        try {
            Order order = objectMapper.readValue(orderJson, Order.class);
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.writeValueAsString(createdOrder));

        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Введены неправильные данные", HttpStatus.BAD_REQUEST);

        }


    }
}
