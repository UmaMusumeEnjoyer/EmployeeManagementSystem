package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeCreateRequest;
import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.UtilityService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) String department) {
        List<EmployeeDTO> employees = employeeService.getAllEmployees(department);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{code}")
    public ResponseEntity<EmployeeDTO> getEmployeeByCode(@PathVariable String code) {
        return employeeService.getEmployeeByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeCreateRequest request) {
        EmployeeDTO createdEmployee = employeeService.addEmployee(request);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/generate-code")
    public ResponseEntity<String> generateCode() {
        return ResponseEntity.ok(utilityService.generateEmployeeCode());
    }

    @GetMapping("/format")
    public ResponseEntity<String> format(@RequestParam String input) {
        return ResponseEntity.ok(utilityService.formatString(input));
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
