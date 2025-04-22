package com.exam.employeeSalaryApp.controller;

import com.exam.employeeSalaryApp.model.Employee;
import com.exam.employeeSalaryApp.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private Model model;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showEmployees_WithNoId_ShouldReturnAllEmployees() {
        // Arrange
        List<Employee> employees = Arrays.asList(
            new Employee("1", "John Doe", 5000.0, 30, ""),
            new Employee("2", "Jane Doe", 6000.0, 25, "")
        );
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // Act
        String viewName = employeeController.showEmployees(null, model);

        // Assert
        assertEquals("index", viewName);
        verify(model).addAttribute("employees", employees);
        verify(employeeService).getAllEmployees();
    }

    @Test
    void showEmployees_WithValidId_ShouldReturnSingleEmployee() {
        // Arrange
        Employee employee = new Employee("1", "John Doe", 5000.0, 30, "");
        when(employeeService.getEmployeeById("1")).thenReturn(employee);

        // Act
        String viewName = employeeController.showEmployees("1", model);

        // Assert
        assertEquals("index", viewName);
        verify(model).addAttribute("employees", List.of(employee));
        verify(employeeService).getEmployeeById("1");
    }

    @Test
    void showEmployees_WithError_ShouldReturnErrorView() {
        // Arrange
        when(employeeService.getEmployeeById("999"))
            .thenThrow(new RuntimeException("Employee not found"));

        // Act
        String viewName = employeeController.showEmployees("999", model);

        // Assert
        assertEquals("error", viewName);
        verify(model).addAttribute(eq("error"), anyString());
    }

    @Test
    void showAbout_ShouldReturnAboutView() {
        // Act
        String viewName = employeeController.showAbout();

        // Assert
        assertEquals("about", viewName);
    }
}