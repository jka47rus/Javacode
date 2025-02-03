package com.example.projections.repository;


import com.example.projections.entity.Employee;
import com.example.projections.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e.firstName AS firstName, e.lastName AS lastName, e.position AS position, d.name AS departmentName " +
            "FROM Employee e JOIN e.department d WHERE e.id = :id")
    EmployeeProjection findEmployeeProjectionById(@Param("id") Long id);

    @Query("SELECT e.firstName AS firstName, e.lastName AS lastName, e.position AS position, d.name AS departmentName " +
            "FROM Employee e JOIN e.department d")
    List<EmployeeProjection> findAllEmployeeProjections();
}
