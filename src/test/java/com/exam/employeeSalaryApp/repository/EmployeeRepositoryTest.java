package com.exam.employeeSalaryApp.repository;

import com.exam.employeeSalaryApp.model.Employee;
import com.exam.employeeSalaryApp.model.EmployeeListResponse;
import com.exam.employeeSalaryApp.model.SingleEmployeeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
        EmployeeListResponse mockResponse = new EmployeeListResponse();
        mockResponse.setStatus("success");
        List<Employee> expectedEmployees = Arrays.asList(
            new Employee(1, "John", 5000.0, 30, ""),
            new Employee(2, "Jane", 6000.0, 25, "")
        );
        mockResponse.setData(expectedEmployees);
        ResponseEntity<EmployeeListResponse> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.getForEntity(anyString(), eq(EmployeeListResponse.class)))
            .thenReturn(responseEntity);

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
        EmployeeListResponse mockResponse = new EmployeeListResponse();
        mockResponse.setStatus("success");
        ResponseEntity<EmployeeListResponse> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.getForEntity(anyString(), eq(EmployeeListResponse.class)))
            .thenReturn(responseEntity);

        // Act
        List<Employee> result = employeeRepository.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getEmployeeById_Success() {
        // Arrange
        SingleEmployeeResponse mockResponse = new SingleEmployeeResponse();
        mockResponse.setStatus("success");
        Employee expectedEmployee = new Employee(1, "John", 5000.0, 30, "");
        mockResponse.setData(expectedEmployee);
        ResponseEntity<SingleEmployeeResponse> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.getForEntity(anyString(), eq(SingleEmployeeResponse.class)))
            .thenReturn(responseEntity);

        // Act
        Employee result = employeeRepository.getEmployeeById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John", result.getEmployeeName());
    }

    @Test
    void getEmployeeById_NotFound() {
        // Arrange
        SingleEmployeeResponse mockResponse = new SingleEmployeeResponse();
        mockResponse.setStatus("error");
        mockResponse.setMessage("Employee not found");
        ResponseEntity<SingleEmployeeResponse> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.getForEntity(anyString(), eq(SingleEmployeeResponse.class)))
            .thenReturn(responseEntity);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> 
            employeeRepository.getEmployeeById(999)
        );
        assertTrue(exception.getMessage().contains("Failed to fetch employee"));
    }

    @Test
    void getAllEmployees_WithRetry() {
        // Arrange
        when(restTemplate.getForEntity(anyString(), eq(EmployeeListResponse.class)))
            .thenThrow(HttpClientErrorException.TooManyRequests.class)
            .thenThrow(HttpClientErrorException.TooManyRequests.class)
            .thenReturn(ResponseEntity.ok(new EmployeeListResponse()));

        // Act
        List<Employee> result = employeeRepository.getAllEmployees();

        // Assert
        assertNotNull(result);
        verify(restTemplate, times(3)).getForEntity(anyString(), eq(EmployeeListResponse.class));
    }

    @Test
    void getEmployeeById_MaxRetriesExceeded() {
        // Arrange
        when(restTemplate.getForEntity(anyString(), eq(SingleEmployeeResponse.class)))
            .thenThrow(HttpClientErrorException.TooManyRequests.class);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
            employeeRepository.getEmployeeById(1)
        );
        assertTrue(exception.getMessage().contains("Too many requests"));
    }
}