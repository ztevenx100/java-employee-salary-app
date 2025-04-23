package com.exam.employeeSalaryApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * Employee entity class that represents an employee in the system.
 * This class follows the principles of encapsulation and data validation.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {
    
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("employee_salary")
    private Double monthlySalary;

    @JsonProperty("employee_age")
    private Integer age;

    @JsonProperty("profile_image")
    private String profileImage = "";

    /**
     * Constructs a new Employee with all required fields.
     */
    public Employee(Integer id, String employeeName, Double monthlySalary, Integer age, String profileImage) {
        setId(id);
        setEmployeeName(employeeName);
        setMonthlySalary(monthlySalary);
        setAge(age);
        setProfileImage(profileImage);
    }

    /**
     * Default constructor required for JSON deserialization.
     */
    public Employee() {
        // profileImage already initialized to "" by field declaration
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        if (employeeName == null || employeeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.employeeName = employeeName.trim();
    }

    public Double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(Double monthlySalary) {
        if (monthlySalary == null || monthlySalary < 0) {
            throw new IllegalArgumentException("Salary cannot be null or negative");
        }
        this.monthlySalary = monthlySalary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        if (age == null || age <= 0) {
            throw new IllegalArgumentException("Age must be a positive integer");
        }
        this.age = age;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage != null ? profileImage.trim() : "";
    }

    /**
     * Calculates the annual salary based on the monthly salary.
     * Annual salary is computed as 12 times the monthly salary.
     *
     * @return the calculated annual salary
     */
    public double calculateAnnualSalary() {
        if (monthlySalary == null) {
            return 0.0;
        }
        return this.monthlySalary * 12;
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
            "id=" + id +
            ", name='" + employeeName + '\'' +
            ", monthlySalary=" + monthlySalary +
            ", age=" + age +
            ", profileImage='" + profileImage + '\'' +
            '}';
    }
}