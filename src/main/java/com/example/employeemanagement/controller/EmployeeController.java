package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeCreateRequest;
import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.dto.EmployeeUpdateRequest;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.UtilityService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final DepartmentRepository departmentRepository;

    public EmployeeController(
            EmployeeService employeeService,
            UtilityService utilityService,
            ModelMapper modelMapper,
            DepartmentRepository departmentRepository
    ) {
        this.employeeService = employeeService;
        this.utilityService = utilityService;
        this.modelMapper = modelMapper;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<EmployeeDTO> getEmployeeByCode(@PathVariable String code) {
        EmployeeDTO employee = employeeService.getEmployeeByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with code: " + code));
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDTO>> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department
    ) {
        return ResponseEntity.ok(employeeService.searchEmployees(name, department));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeCreateRequest request) {
        EmployeeDTO created = employeeService.createEmployee(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateRequest request
    ) {
        EmployeeDTO updated = employeeService.updateEmployee(id, request)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/report/total")
    public ResponseEntity<Long> getTotalEmployees() {
        return ResponseEntity.ok(employeeService.countTotalEmployees());
    }

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentRepository.findAll());
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
