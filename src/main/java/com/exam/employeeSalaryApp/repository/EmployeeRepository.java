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
    private static final int INITIAL_WAIT_TIME = 1000;

    private final RestTemplate restTemplate;

    public EmployeeRepository() {
        this.restTemplate = new RestTemplate();
    }

    public List<Employee> getAllEmployees() {
        return executeWithRetries(() -> {
            ApiResponse response = restTemplate.getForObject(BASE_URL + "/employees", ApiResponse.class);
            return response != null && response.getData() != null ? response.getData() : Collections.emptyList();
        });
    }

    public Employee getEmployeeById(String id) {
        return (Employee) executeWithRetries(() -> {
            ApiResponse response = restTemplate.getForObject(BASE_URL + "/employee/" + id, ApiResponse.class);
            if (response != null && response.getData() instanceof Employee) {
                return response.getData();
            }
            throw new RuntimeException("Employee not found or invalid response structure.");
        });
    }

    private <T> T executeWithRetries(RetryableTask<T> task) {
        int waitTime = INITIAL_WAIT_TIME;
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                return task.execute();
            } catch (HttpClientErrorException.TooManyRequests e) {
                System.out.println("Attempt " + attempt + " failed: Too Many Requests. Retrying...");
                if (attempt == MAX_RETRIES) {
                    throw new RuntimeException("Max retries reached. Unable to complete the request.", e);
                }
                sleep(waitTime);
                waitTime *= 2; // Exponential backoff
            }
        }
        throw new RuntimeException("Unexpected error: retries exhausted.");
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
        T execute() throws HttpClientErrorException.TooManyRequests;
    }
}