package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final UtilityService utilityService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(UtilityService utilityService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.utilityService = utilityService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public EmployeeDTO createEmployee(String rawName, String department, String rawPassword) {
        String formattedName = utilityService.formatString(rawName);
        String code = utilityService.generateEmployeeCode();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Employee employee = new Employee(code, formattedName, department, encodedPassword);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public UtilityService getUtilityService() {
        return utilityService;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
