package com.example.MVCObject.mapper.repository;

import com.example.MVCObject.mapper.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}