package com.example.projections.controller;

import com.example.projections.entity.Department;
import com.example.projections.service.DepartmentService;
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
public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @Test
    public void testGetAllDepartments() {
        Department department = new Department();
        department.setName("IT");
        when(departmentService.getAllDepartments()).thenReturn(List.of(department));

        List<Department> departments = departmentController.getAllDepartments();

        assertThat(departments).hasSize(1);
        assertThat(departments.get(0).getName()).isEqualTo("IT");
        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    public void testGetDepartmentById_Found() {
        Department department = new Department();
        department.setId(1L);
        department.setName("IT");
        when(departmentService.getDepartmentById(1L)).thenReturn(Optional.of(department));

        ResponseEntity<Department> response = departmentController.getDepartmentById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("IT");
        verify(departmentService, times(1)).getDepartmentById(1L);
    }

    @Test
    public void testGetDepartmentById_NotFound() {
        when(departmentService.getDepartmentById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Department> response = departmentController.getDepartmentById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(departmentService, times(1)).getDepartmentById(1L);
    }

    @Test
    public void testCreateDepartment() {
        Department department = new Department();
        department.setName("IT");
        when(departmentService.saveDepartment(department)).thenReturn(department);

        ResponseEntity<Department> response = departmentController.createDepartment(department);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("IT");
        verify(departmentService, times(1)).saveDepartment(department);
    }

    @Test
    public void testUpdateDepartment() {
        Department department = new Department();
        department.setId(1L);
        department.setName("IT");
        when(departmentService.saveDepartment(department)).thenReturn(department);

        ResponseEntity<Department> response = departmentController.updateDepartment(1L, department);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("IT");
        verify(departmentService, times(1)).saveDepartment(department);
    }

    @Test
    public void testDeleteDepartment() {
        ResponseEntity<Void> response = departmentController.deleteDepartment(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(departmentService, times(1)).deleteDepartment(1L);
    }
}
