package com.exam.employeeSalaryApp.service;

import com.exam.employeeSalaryApp.model.Employee;
import com.exam.employeeSalaryApp.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() {
        // Arrange
        List<Employee> expectedEmployees = Arrays.asList(
            new Employee("1", "John Doe", 5000.0, 30, ""),
            new Employee("2", "Jane Doe", 6000.0, 25, "")
        );
        when(employeeRepository.getAllEmployees()).thenReturn(expectedEmployees);

        // Act
        List<Employee> actualEmployees = employeeService.getAllEmployees();

        // Assert
        assertNotNull(actualEmployees);
        assertEquals(2, actualEmployees.size());
        assertEquals(expectedEmployees, actualEmployees);
        verify(employeeRepository).getAllEmployees();
    }

    @Test
    void testCalculateAnnualSalary() {
        // Arrange
        Employee employee = new Employee("1", "John Doe", 5000.0, 30, "");
        when(employeeRepository.getEmployeeById("1")).thenReturn(employee);

        // Act
        Employee result = employeeService.getEmployeeById("1");

        // Assert
        assertNotNull(result);
        assertEquals(60000.0, result.calculateAnnualSalary(), 
            "Annual salary should be 12 times the monthly salary");
    }

    @Test
    void getEmployeeById_WithValidId_ShouldReturnEmployee() {
        // Arrange
        Employee expected = new Employee("1", "John Doe", 5000.0, 30, "");
        when(employeeRepository.getEmployeeById("1")).thenReturn(expected);

        // Act
        Employee actual = employeeService.getEmployeeById("1");

        // Assert
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getEmployeeName(), actual.getEmployeeName());
        assertEquals(expected.getMonthlySalary(), actual.getMonthlySalary());
        verify(employeeRepository).getEmployeeById("1");
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // Arrange
        when(employeeRepository.getEmployeeById("999"))
            .thenThrow(new RuntimeException("Employee not found"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeService.getEmployeeById("999");
        });

        assertEquals("Employee not found", exception.getMessage());
    }

    @Test
    void getEmployeeById_WithNullId_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.getEmployeeById(null);
        });
        verify(employeeRepository, never()).getEmployeeById(any());
    }
}