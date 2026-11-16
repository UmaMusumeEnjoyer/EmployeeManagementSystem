package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.UtilityService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UtilityService utilityService;
    private final ModelMapper modelMapper;

    public EmployeeController(EmployeeService employeeService, UtilityService utilityService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.utilityService = utilityService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public EmployeeDTO createEmployee(@RequestParam String name, @RequestParam String department, @RequestParam(defaultValue = "123456") String password) {
        return employeeService.createEmployee(name, department, password);
    }

    @GetMapping("/generate-code")
    public String generateCode() {
        return utilityService.generateEmployeeCode();
    }

    @GetMapping("/format")
    public String format(@RequestParam String input) {
        return utilityService.formatString(input);
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public UtilityService getUtilityService() {
        return utilityService;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }
}
