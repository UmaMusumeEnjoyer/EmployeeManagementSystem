package com.example.employeemanagement.dto;

import java.util.List;

public class EmployeeStatisticsDTO {

    private long totalEmployees;
    private List<DepartmentStatisticDTO> departmentStatistics;

    public EmployeeStatisticsDTO() {
    }

    public EmployeeStatisticsDTO(long totalEmployees, List<DepartmentStatisticDTO> departmentStatistics) {
        this.totalEmployees = totalEmployees;
        this.departmentStatistics = departmentStatistics;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public List<DepartmentStatisticDTO> getDepartmentStatistics() {
        return departmentStatistics;
    }

    public void setDepartmentStatistics(List<DepartmentStatisticDTO> departmentStatistics) {
        this.departmentStatistics = departmentStatistics;
    }
}
