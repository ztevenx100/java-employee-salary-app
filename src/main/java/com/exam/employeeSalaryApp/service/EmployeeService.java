package com.exam.employeeSalaryApp.service;

import com.exam.employeeSalaryApp.model.Employee;
import com.exam.employeeSalaryApp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Cacheable("employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Cacheable(value = "employee", key = "#id")
    public Employee getEmployeeById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return employeeRepository.getEmployeeById(id);
    }
}