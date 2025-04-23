package com.exam.employeeSalaryApp.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void constructor_WithValidData_ShouldCreateEmployee() {
        // Act
        Employee employee = new Employee(1, "John Doe", 5000.0, 30, "profile.jpg");

        // Assert
        assertEquals(1, employee.getId());
        assertEquals("John Doe", employee.getEmployeeName());
        assertEquals(5000.0, employee.getMonthlySalary());
        assertEquals(30, employee.getAge());
        assertEquals("profile.jpg", employee.getProfileImage());
    }

    @Test
    void constructor_Default_ShouldCreateEmptyEmployee() {
        // Act
        Employee employee = new Employee();

        // Assert
        assertNull(employee.getId());
        assertNull(employee.getEmployeeName());
        assertNull(employee.getMonthlySalary());
        assertNull(employee.getAge());
        assertEquals("", employee.getProfileImage());
    }

    @Test
    void calculateAnnualSalary_ShouldReturnCorrectValue() {
        // Arrange
        Employee employee = new Employee(1, "John Doe", 5000.0, 30, "");

        // Act
        double annualSalary = employee.calculateAnnualSalary();

        // Assert
        assertEquals(60000.0, annualSalary, "Annual salary should be 12 times monthly salary");
    }

    @Test
    void setMonthlySalary_WithNegativeValue_ShouldThrowException() {
        // Arrange
        Employee employee = new Employee();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            employee.setMonthlySalary(-1000.0);
        });
    }

    @Test
    void setAge_WithZeroOrNegative_ShouldThrowException() {
        // Arrange
        Employee employee = new Employee();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            employee.setAge(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            employee.setAge(-1);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void setEmployeeName_WithInvalidInput_ShouldThrowException(String invalidName) {
        // Arrange
        Employee employee = new Employee();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            employee.setEmployeeName(invalidName);
        });
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        Employee employee1 = new Employee(1, "John Doe", 5000.0, 30, "");
        Employee employee2 = new Employee(1, "Different Name", 6000.0, 40, "");

        // Act & Assert
        assertEquals(employee1, employee2);
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void equals_WithDifferentId_ShouldReturnFalse() {
        // Arrange
        Employee employee1 = new Employee(1, "John Doe", 5000.0, 30, "");
        Employee employee2 = new Employee(2, "John Doe", 5000.0, 30, "");

        // Act & Assert
        assertNotEquals(employee1, employee2);
    }

    @Test
    void toString_ShouldContainAllFields() {
        // Arrange
        Employee employee = new Employee(1, "John Doe", 5000.0, 30, "profile.jpg");

        // Act
        String result = employee.toString();

        // Assert
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("name='John Doe'"));
        assertTrue(result.contains("monthlySalary=5000.0"));
        assertTrue(result.contains("age=30"));
        assertTrue(result.contains("profileImage='profile.jpg'"));
    }
}