package com.exam.employeeSalaryApp.model;

import java.util.Objects;

/**
 * Employee entity class that represents an employee in the system.
 * This class follows the principles of encapsulation and data validation.
 */
public class Employee {
    
    private String id;
    private String employeeName; // Renamed for Java naming conventions
    private double monthlySalary; // Renamed for clarity
    private int age; // Simplified name
    private String profileImage; // Renamed for Java naming conventions

    /**
     * Constructs a new Employee with all required fields.
     *
     * @param id Employee's unique identifier
     * @param employeeName Employee's full name
     * @param monthlySalary Employee's monthly salary
     * @param age Employee's age
     * @param profileImage URL or path to employee's profile image
     */
    public Employee(String id, String employeeName, double monthlySalary, int age, String profileImage) {
        setId(id); // Using setters for validation
        setEmployeeName(employeeName);
        setMonthlySalary(monthlySalary);
        setAge(age);
        setProfileImage(profileImage);
    }

    /**
     * Default constructor required for JSON deserialization.
     */
    public Employee() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        this.id = id.trim();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    // Maintaining compatibility with API response
    public String getEmployee_name() {
        return getEmployeeName();
    }

    public void setEmployeeName(String employeeName) {
        if (employeeName == null || employeeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.employeeName = employeeName.trim();
    }

    // Maintaining compatibility with API response
    public void setEmployee_name(String employee_name) {
        setEmployeeName(employee_name);
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    // Maintaining compatibility with API response
    public double getEmployee_salary() {
        return getMonthlySalary();
    }

    public void setMonthlySalary(double monthlySalary) {
        if (monthlySalary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.monthlySalary = monthlySalary;
    }

    // Maintaining compatibility with API response
    public void setEmployee_salary(double employee_salary) {
        setMonthlySalary(employee_salary);
    }

    public int getAge() {
        return age;
    }

    // Maintaining compatibility with API response
    public int getEmployee_age() {
        return getAge();
    }

    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be a positive integer");
        }
        this.age = age;
    }

    // Maintaining compatibility with API response
    public void setEmployee_age(int employee_age) {
        setAge(employee_age);
    }

    public String getProfileImage() {
        return profileImage;
    }

    // Maintaining compatibility with API response
    public String getProfile_image() {
        return getProfileImage();
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage != null ? profileImage.trim() : "";
    }

    // Maintaining compatibility with API response
    public void setProfile_image(String profile_image) {
        setProfileImage(profile_image);
    }

    /**
     * Calculates the annual salary based on the monthly salary.
     * Annual salary is computed as 12 times the monthly salary.
     *
     * @return the calculated annual salary
     */
    public double calculateAnnualSalary() {
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
            "id='" + id + '\'' +
            ", name='" + employeeName + '\'' +
            ", monthlySalary=" + monthlySalary +
            ", age=" + age +
            ", profileImage='" + profileImage + '\'' +
            '}';
    }
}