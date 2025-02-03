package com.example.projections.projection;

//import org.springframework.data.rest.core.config.Projection;

//@Projection(name = "employeeProjection", types = { Employee.class })
public interface EmployeeProjection {

    String getFirstName();

    String getLastName();

    String getPosition();

    String getDepartmentName();

    default String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}