package com.exam.employeeSalaryApp.repository;

import com.exam.employeeSalaryApp.model.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final String BASE_URL = "http://dummy.restapiexample.com/api/v1";

    public List<Employee> getAllEmployees() {
        RestTemplate restTemplate = new RestTemplate();
        Employee[] employees = restTemplate.getForObject(BASE_URL + "/employees", Employee[].class);
        return Arrays.asList(employees);
    }

    public Employee getEmployeeById(String id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(BASE_URL + "/employee/" + id, Employee.class);
    }
}