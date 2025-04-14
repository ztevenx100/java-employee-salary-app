package com.exam.employeeSalaryApp.model;

import java.util.Objects;

public class Employee {
    
    private String id;
    private String employee_name;
    private double employee_salary;
    private int employee_age;
    private String profile_image;

    public Employee(String id, String employee_name, double employee_salary, int employee_age, String profile_image) {
        this.id = id;
        this.employee_name = employee_name;
        this.employee_salary = employee_salary;
        this.employee_age = employee_age;
        this.profile_image = profile_image;
    }

    public Employee() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        this.id = id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        if (employee_name == null || employee_name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.employee_name = employee_name;
    }

    public double getEmployee_salary() {
        return employee_salary;
    }

    public void setEmployee_salary(double employee_salary) {
        if (employee_salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.employee_salary = employee_salary;
    }

    public int getEmployee_age() {
        return employee_age;
    }

    public void setEmployee_age(int employee_age) {
        if (employee_age <= 0) {
            throw new IllegalArgumentException("Age must be a positive integer");
        }
        this.employee_age = employee_age;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public double getAnnualSalary() {
        return employee_salary * 12;
    }

    public double calculateAnnualSalary() {
        return employee_salary * 12;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id='" + id + '\'' +
            ", name='" + employee_name + '\'' +
            ", salary=" + employee_salary +
            ", age=" + employee_age +
            ", profileImage='" + profile_image + '\'' +
            '}';
    }
}