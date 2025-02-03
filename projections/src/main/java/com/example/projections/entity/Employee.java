package com.example.projections.entity;

import com.example.projections.dto.EmployeeSummary;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(EmployeeSummary.class)
    private Long id;
    @JsonView(EmployeeSummary.class)
    private String firstName;
    @JsonView(EmployeeSummary.class)
    private String lastName;
    @JsonView(EmployeeSummary.class)
    private String position;
    @JsonView(EmployeeSummary.class)
    private double salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}