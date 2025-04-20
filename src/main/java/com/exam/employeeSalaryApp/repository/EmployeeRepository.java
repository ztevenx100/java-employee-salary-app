package com.exam.employeeSalaryApp.repository;

import com.exam.employeeSalaryApp.model.ApiResponse;
import com.exam.employeeSalaryApp.model.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Repository
public class EmployeeRepository {

    private static final String BASE_URL = "http://dummy.restapiexample.com/api/v1";
    private static final int MAX_RETRIES = 3;
    private static final int INITIAL_WAIT_TIME = 3000;

    private final RestTemplate restTemplate;

    public EmployeeRepository() {
        this.restTemplate = new RestTemplate();
    }

    public List<Employee> getAllEmployees() {
        String url = BASE_URL + "/employees";
        System.out.println("Fetching all employees from: " + url);
        return executeWithRetries(() -> {
            ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
            if (response != null && response.getData() != null) {
                return response.getData();
            }
            return Collections.emptyList();
        });
    }

    public Employee getEmployeeById(String id) {
        String url = BASE_URL + "/employee/" + id;
        System.out.println("Fetching employee with ID " + id + " from: " + url);
        return executeWithRetries(() -> {
            ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
            if (response != null && response.getData() != null) {
                return (Employee) response.getData();
            }
            throw new RuntimeException("Employee not found with ID: " + id);
        });
    }

    private <T> T executeWithRetries(RetryableTask<T> task) {
        int waitTime = INITIAL_WAIT_TIME;
        RuntimeException lastException = null;
        
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                return task.execute();
            } catch (HttpClientErrorException.TooManyRequests e) {
                lastException = new RuntimeException("Too many requests. Please try again later.", e);
                System.out.println("Attempt " + attempt + " failed: Too Many Requests. Retrying...");
                if (attempt < MAX_RETRIES) {
                    sleep(waitTime);
                    waitTime *= 2;
                }
            } catch (Exception e) {
                lastException = new RuntimeException("Error accessing the employee API: " + e.getMessage(), e);
                if (attempt < MAX_RETRIES) {
                    sleep(waitTime);
                    waitTime *= 2;
                }
            }
        }
        throw lastException != null ? lastException : 
            new RuntimeException("Failed to complete the request after " + MAX_RETRIES + " attempts");
    }

    private void sleep(int waitTime) {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while waiting", e);
        }
    }

    @FunctionalInterface
    private interface RetryableTask<T> {
        T execute() throws Exception;
    }
}