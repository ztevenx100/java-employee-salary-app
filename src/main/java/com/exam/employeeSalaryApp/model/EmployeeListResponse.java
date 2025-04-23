package com.exam.employeeSalaryApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeListResponse {
    private String status;
    private List<Employee> data;
    private String message;

    // Constructor por defecto necesario para Jackson
    public EmployeeListResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Employee> getData() {
        return data;
    }

    public void setData(List<Employee> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EmployeeListResponse{" +
            "status='" + status + '\'' +
            ", data=" + data +
            ", message='" + message + '\'' +
            '}';
    }
}