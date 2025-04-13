package com.exam.employeeSalaryApp.repository;

import com.exam.employeeSalaryApp.model.ApiResponse;
import com.exam.employeeSalaryApp.model.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class EmployeeRepository {

    private final String BASE_URL = "http://dummy.restapiexample.com/api/v1";

    public List<Employee> getAllEmployees() {
        int maxRetries = 3;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                Thread.sleep(1000);

                RestTemplate restTemplate = new RestTemplate();
                ApiResponse response = restTemplate.getForObject(BASE_URL + "/employees", ApiResponse.class);
                if (response != null && response.getData() != null) {
                    return response.getData();
                }
                return List.of();
            } catch (HttpClientErrorException.TooManyRequests e) {
                System.out.println("Attempt " + attempt + " failed: Too Many Requests. Retrying...");
                if (attempt == maxRetries) {
                    throw new RuntimeException("Max retries reached. Unable to fetch employees.", e);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted while waiting", e);
            }
        }
        return List.of();
    }

    public Employee getEmployeeById(String id) {
        int maxRetries = 3;
        int waitTime = 1000;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                Thread.sleep(waitTime);

                RestTemplate restTemplate = new RestTemplate();
                return restTemplate.getForObject(BASE_URL + "/employee/" + id, Employee.class);
            } catch (HttpClientErrorException.TooManyRequests e) {
                System.out.println("Attempt " + attempt + " failed: Too Many Requests. Retrying...");
                if (attempt == maxRetries) {
                    throw new RuntimeException("Max retries reached. Unable to fetch employee with ID: " + id, e);
                }
                waitTime *= 2;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted while waiting", e);
            }
        }
        return null;
    }
}