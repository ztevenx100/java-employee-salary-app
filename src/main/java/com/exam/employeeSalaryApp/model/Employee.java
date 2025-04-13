// filepath: d:\Programacion\webAppsGit\java-employee-salary-app\src\main\java\com\exam\employeeSalaryApp\model\Employee.java
package com.exam.employeeSalaryApp.model;

public class Employee {
    private String id;
    private String employee_name;
    private double employee_salary;
    private String employee_age;
    private String profile_image;

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public double getEmployee_salary() {
        return employee_salary;
    }

    public void setEmployee_salary(double employee_salary) {
        this.employee_salary = employee_salary;
    }

    public String getEmployee_age() {
        return employee_age;
    }

    public void setEmployee_age(String employee_age) {
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
}