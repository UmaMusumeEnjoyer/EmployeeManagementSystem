package com.example.employeemanagement.model;

public class Employee {

    private String code;
    private String name;
    private String department;
    private String password;

    public Employee() {
    }

    public Employee(String code, String name, String department, String password) {
        this.code = code;
        this.name = name;
        this.department = department;
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
