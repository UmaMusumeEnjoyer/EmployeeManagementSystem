package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeCreateRequest;
import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.model.Employee;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class EmployeeService {

    private final UtilityService utilityService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final List<Employee> employeeList = new CopyOnWriteArrayList<>();

    public EmployeeService(UtilityService utilityService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.utilityService = utilityService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initData() {
        addEmployee(new EmployeeCreateRequest("Nguyen Van A", "IT", "123456"));
        addEmployee(new EmployeeCreateRequest("Tran Thi B", "HR", "123456"));
    }

    public List<EmployeeDTO> getAllEmployees(String department) {
        return employeeList.stream()
                .filter(emp -> department == null || department.isBlank() || emp.getDepartment().equalsIgnoreCase(department))
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                .toList();
    }

    public Optional<EmployeeDTO> getEmployeeByCode(String code) {
        return employeeList.stream()
                .filter(emp -> emp.getCode().equalsIgnoreCase(code))
                .findFirst()
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class));
    }

    public EmployeeDTO addEmployee(EmployeeCreateRequest request) {
        String formattedName = utilityService.formatString(request.getName());
        String code = utilityService.generateEmployeeCode();
        String rawPassword = request.getPassword() == null || request.getPassword().isBlank() ? "123456" : request.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Employee employee = new Employee(code, formattedName, request.getDepartment(), encodedPassword);
        employeeList.add(employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public EmployeeDTO createEmployee(String rawName, String department, String rawPassword) {
        return addEmployee(new EmployeeCreateRequest(rawName, department, rawPassword));
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
