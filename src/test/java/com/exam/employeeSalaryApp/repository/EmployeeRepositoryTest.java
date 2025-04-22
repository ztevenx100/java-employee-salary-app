package com.exam.employeeSalaryApp.repository;

import com.exam.employeeSalaryApp.model.ApiResponse;
import com.exam.employeeSalaryApp.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EmployeeRepositoryTest {

    @Mock
    private RestTemplate restTemplate;

    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeRepository = new EmployeeRepository();
        // Inyectamos el mock de RestTemplate usando reflexión
        try {
            java.lang.reflect.Field field = EmployeeRepository.class.getDeclaredField("restTemplate");
            field.setAccessible(true);
            field.set(employeeRepository, restTemplate);
        } catch (Exception e) {
            fail("Failed to inject mock RestTemplate: " + e.getMessage());
        }
    }

    @Test
    void getAllEmployees_Success() {
        // Arrange
        ApiResponse mockResponse = new ApiResponse();
        List<Employee> expectedEmployees = Arrays.asList(
            new Employee("1", "John", 5000.0, 30, ""),
            new Employee("2", "Jane", 6000.0, 25, "")
        );
        mockResponse.setData(expectedEmployees);
        when(restTemplate.getForObject(anyString(), eq(ApiResponse.class)))
            .thenReturn(mockResponse);

        // Act
        List<Employee> result = employeeRepository.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getEmployeeName());
        assertEquals("Jane", result.get(1).getEmployeeName());
    }

    @Test
    void getAllEmployees_EmptyResponse() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(ApiResponse.class)))
            .thenReturn(new ApiResponse());

        // Act
        List<Employee> result = employeeRepository.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getEmployeeById_Success() {
        // Arrange
        ApiResponse mockResponse = new ApiResponse();
        Employee expectedEmployee = new Employee("1", "John", 5000.0, 30, "");
        mockResponse.setData(expectedEmployee);
        when(restTemplate.getForObject(anyString(), eq(ApiResponse.class)))
            .thenReturn(mockResponse);

        // Act
        Employee result = employeeRepository.getEmployeeById("1");

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("John", result.getEmployeeName());
    }

    @Test
    void getEmployeeById_NotFound() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(ApiResponse.class)))
            .thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            employeeRepository.getEmployeeById("999")
        );
    }

    @Test
    void getAllEmployees_WithRetry() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(ApiResponse.class)))
            .thenThrow(HttpClientErrorException.TooManyRequests.class)
            .thenThrow(HttpClientErrorException.TooManyRequests.class)
            .thenReturn(new ApiResponse());

        // Act
        List<Employee> result = employeeRepository.getAllEmployees();

        // Assert
        assertNotNull(result);
        verify(restTemplate, times(3)).getForObject(anyString(), eq(ApiResponse.class));
    }

    @Test
    void getEmployeeById_MaxRetriesExceeded() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(ApiResponse.class)))
            .thenThrow(HttpClientErrorException.TooManyRequests.class);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
            employeeRepository.getEmployeeById("1")
        );
        assertTrue(exception.getMessage().contains("Too many requests"));
    }
}