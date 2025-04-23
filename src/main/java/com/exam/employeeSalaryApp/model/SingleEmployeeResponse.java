package com.exam.employeeSalaryApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleEmployeeResponse {
    private String status;
    private Employee data;
    private String message;

    // Constructor por defecto necesario para Jackson
    public SingleEmployeeResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getData() {
        return data;
    }

    public void setData(Employee data) {
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
        return "SingleEmployeeResponse{" +
            "status='" + status + '\'' +
            ", data=" + data +
            ", message='" + message + '\'' +
            '}';
    }
}