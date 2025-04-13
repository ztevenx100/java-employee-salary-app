package com.exam.employeeSalaryApp.controller;

import com.exam.employeeSalaryApp.model.Employee;
import com.exam.employeeSalaryApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String showEmployees(@RequestParam(required = false) String id, Model model) {
        if (id == null || id.isEmpty()) {
            List<Employee> employees = employeeService.getAllEmployees();
            System.out.println("All Employees: " + employees);
            model.addAttribute("employees", employees);
        } else {
            Employee employee = employeeService.getEmployeeById(id);
            model.addAttribute("employees", List.of(employee));
        }
        return "index";
    }
}