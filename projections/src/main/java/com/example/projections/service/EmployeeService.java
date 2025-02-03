package com.example.projections.service;

import com.example.projections.entity.Employee;
import com.example.projections.projection.EmployeeProjection;
import com.example.projections.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<EmployeeProjection> getAllEmployeeProjections() {
        return employeeRepository.findAllEmployeeProjections();
    }

    public EmployeeProjection getEmployeeProjectionById(Long id) {
        return employeeRepository.findEmployeeProjectionById(id);
    }
}