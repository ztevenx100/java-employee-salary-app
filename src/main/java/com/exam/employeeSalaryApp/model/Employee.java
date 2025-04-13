package com.exam.employeeSalaryApp.model;

public class Employee {
    private String id;
    private String employee_name;
    private double employee_salary;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmployeeName() { return employee_name; }
    public void setEmployeeName(String employee_name) { this.employee_name = employee_name; }

    public double getEmployeeSalary() { return employee_salary; }
    public void setEmployeeSalary(double employee_salary) { this.employee_salary = employee_salary; }

    // Método para calcular el salario anual
    public double getAnnualSalary() {
        return employee_salary * 12;
    }
}