package com.exam.employeeSalaryApp.service;

import com.exam.employeeSalaryApp.model.Employee;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceTest {

    @Test
    public void testCalculateAnnualSalary() {
        Employee employee = new Employee();
        employee.setEmployee_salary(5000);
        assertEquals(60000, employee.calculateAnnualSalary());
    }
}