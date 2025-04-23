package com.exam.employeeSalaryApp.repository;

import com.exam.employeeSalaryApp.model.Employee;
import com.exam.employeeSalaryApp.model.EmployeeListResponse;
import com.exam.employeeSalaryApp.model.SingleEmployeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Repository
public class EmployeeRepository {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeRepository.class);
    private static final String BASE_URL = "http://dummy.restapiexample.com/api/v1";
    private static final int MAX_RETRIES = 5;
    private static final long INITIAL_WAIT_TIME = 5000; // 5 seconds
    private static final long MAX_WAIT_TIME = 30000; // 30 seconds
    private final RestTemplate restTemplate;
    private final Random random;

    public EmployeeRepository() {
        this.restTemplate = new RestTemplate();
        this.random = new Random();
        
        // Configure RestTemplate for better JSON deserialization
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_OCTET_STREAM
        ));
        
        this.restTemplate.getMessageConverters().add(0, converter);
    }

    public List<Employee> getAllEmployees() {
        String url = BASE_URL + "/employees";
        logger.info("Fetching all employees from: {}", url);
        return executeWithRetries(() -> {
            ResponseEntity<EmployeeListResponse> responseEntity = 
                restTemplate.getForEntity(url, EmployeeListResponse.class);
            
            logger.debug("Response status code: {}", responseEntity.getStatusCode());
            
            EmployeeListResponse response = responseEntity.getBody();
            if (response != null) {
                logger.info("Response received: status={}, message={}", 
                           response.getStatus(), response.getMessage());
                if ("success".equals(response.getStatus()) && response.getData() != null) {
                    return response.getData();
                }
            }
            return Collections.emptyList();
        });
    }

    public Employee getEmployeeById(Integer id) {
        String url = BASE_URL + "/employee/" + id;
        logger.info("Fetching employee with ID {} from: {}", id, url);
        return executeWithRetries(() -> {
            ResponseEntity<SingleEmployeeResponse> responseEntity = 
                restTemplate.getForEntity(url, SingleEmployeeResponse.class);
            
            logger.debug("Response status code: {}", responseEntity.getStatusCode());
            
            SingleEmployeeResponse response = responseEntity.getBody();
            if (response != null) {
                logger.info("Response received: status={}, message={}", 
                           response.getStatus(), response.getMessage());
                if ("success".equals(response.getStatus()) && response.getData() != null) {
                    return response.getData();
                }
                throw new RuntimeException("Failed to fetch employee: " + response.getMessage());
            }
            throw new RuntimeException("Employee not found with ID: " + id);
        });
    }

    private <T> T executeWithRetries(RetryableTask<T> task) {
        int attempt = 0;
        RuntimeException lastException = null;
        
        while (attempt < MAX_RETRIES) {
            try {
                attempt++;
                logger.info("Attempt {} of {}", attempt, MAX_RETRIES);
                return task.execute();
            } catch (HttpClientErrorException.TooManyRequests e) {
                lastException = new RuntimeException("Rate limit exceeded", e);
                if (attempt < MAX_RETRIES) {
                    long waitTime = calculateWaitTime(attempt);
                    logger.warn("Rate limit exceeded on attempt {}. Waiting {} ms before retry.", 
                              attempt, waitTime);
                    sleep(waitTime);
                }
            } catch (Exception e) {
                lastException = new RuntimeException("Error accessing the API: " + e.getMessage(), e);
                if (shouldRetry(e) && attempt < MAX_RETRIES) {
                    long waitTime = calculateWaitTime(attempt);
                    logger.warn("Request failed on attempt {}. Waiting {} ms before retry. Error: {}", 
                              attempt, waitTime, e.getMessage());
                    sleep(waitTime);
                } else {
                    break;
                }
            }
        }
        
        throw lastException != null ? lastException : 
            new RuntimeException("Failed to complete the request after " + MAX_RETRIES + " attempts");
    }

    private long calculateWaitTime(int attempt) {
        // Exponential backoff with jitter
        long exponentialWait = INITIAL_WAIT_TIME * (long) Math.pow(2, attempt - 1);
        long maxWaitWithJitter = Math.min(exponentialWait, MAX_WAIT_TIME);
        return maxWaitWithJitter + random.nextInt(1000); // Add up to 1 second of random jitter
    }

    private boolean shouldRetry(Exception e) {
        if (e instanceof HttpClientErrorException) {
            HttpClientErrorException httpError = (HttpClientErrorException) e;
            // Retry on rate limits (429) and server errors (500s)
            return httpError.getStatusCode().value() == 429 
                || httpError.getStatusCode().value() >= 500;
        }
        return false;
    }

    private void sleep(long waitTime) {
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