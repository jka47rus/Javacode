package com.example.MVCObject.mapper.repository;

import com.example.MVCObject.mapper.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}