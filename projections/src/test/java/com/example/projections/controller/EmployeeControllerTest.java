package com.example.projections.controller;

import com.example.projections.entity.Employee;
import com.example.projections.projection.EmployeeProjection;
import com.example.projections.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    public void testGetAllEmployees() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

        List<Employee> employees = employeeController.getAllEmployees();

        assertThat(employees).hasSize(1);
        assertThat(employees.get(0).getFirstName()).isEqualTo("John");
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    public void testGetEmployeeById_Found() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employee));

        ResponseEntity<Employee> response = employeeController.getEmployeeById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("John");
        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    public void testGetEmployeeById_NotFound() {
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Employee> response = employeeController.getEmployeeById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        when(employeeService.saveEmployee(employee)).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.createEmployee(employee);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("John");
        verify(employeeService, times(1)).saveEmployee(employee);
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        when(employeeService.saveEmployee(employee)).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.updateEmployee(1L, employee);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstName()).isEqualTo("John");
        verify(employeeService, times(1)).saveEmployee(employee);
    }

    @Test
    public void testDeleteEmployee() {
        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    public void testGetAllEmployeeProjections() {
        EmployeeProjection projection = new EmployeeProjection() {
            @Override
            public String getFirstName() {
                return "John";
            }

            @Override
            public String getLastName() {
                return "Doe";
            }

            @Override
            public String getPosition() {
                return "Developer";
            }

            @Override
            public String getDepartmentName() {
                return "IT";
            }
        };
        when(employeeService.getAllEmployeeProjections()).thenReturn(List.of(projection));

        List<EmployeeProjection> projections = employeeController.getAllEmployeeProjections();

        assertThat(projections).hasSize(1);
        assertThat(projections.get(0).getFullName()).isEqualTo("John Doe");
        verify(employeeService, times(1)).getAllEmployeeProjections();
    }
}
