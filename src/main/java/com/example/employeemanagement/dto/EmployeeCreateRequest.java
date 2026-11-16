package com.example.employeemanagement.dto;

public class EmployeeCreateRequest {

    private String name;
    private String department;
    private String password;

    public EmployeeCreateRequest() {
    }

    public EmployeeCreateRequest(String name, String department, String password) {
        this.name = name;
        this.department = department;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
