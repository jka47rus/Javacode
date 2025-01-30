package com.example.MVCObject.mapper.repository;

import com.example.MVCObject.mapper.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
