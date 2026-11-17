package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeCreateRequest;
import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.dto.EmployeeUpdateRequest;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UtilityService utilityService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            UtilityService utilityService,
            ModelMapper modelMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.utilityService = utilityService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initData() {
        if (departmentRepository.count() == 0) {
            Department it = departmentRepository.save(new Department("IT"));
            Department hr = departmentRepository.save(new Department("HR"));
            Department finance = departmentRepository.save(new Department("Finance"));

            if (employeeRepository.count() == 0) {
                Employee emp1 = new Employee(
                        utilityService.generateEmployeeCode(),
                        "Nguyen Van A",
                        "nguyenvana@example.com",
                        passwordEncoder.encode("123456"),
                        it
                );
                Employee emp2 = new Employee(
                        utilityService.generateEmployeeCode(),
                        "Tran Thi B",
                        "tranthib@example.com",
                        passwordEncoder.encode("123456"),
                        hr
                );
                employeeRepository.save(emp1);
                employeeRepository.save(emp2);
            }
        }
    }

    public EmployeeDTO createEmployee(EmployeeCreateRequest request) {
        String formattedName = utilityService.formatString(request.getName());
        String code = utilityService.generateEmployeeCode();
        String rawPassword = request.getPassword() == null || request.getPassword().isBlank() ? "123456" : request.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Department department = null;
        if (request.getDepartment() != null && !request.getDepartment().isBlank()) {
            department = departmentRepository.findByNameIgnoreCase(request.getDepartment().trim())
                    .orElseGet(() -> departmentRepository.save(new Department(request.getDepartment().trim())));
        }

        String email = request.getEmail();
        if (email == null || email.isBlank()) {
            email = code.toLowerCase() + "@example.com";
        }

        Employee employee = new Employee(code, formattedName, email, encodedPassword, department);
        Employee saved = employeeRepository.save(employee);
        return mapToDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> getEmployeeByCode(String code) {
        return employeeRepository.findByCode(code)
                .map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> searchEmployees(String name, String department) {
        String nameParam = (name == null || name.isBlank()) ? null : name.trim();
        String deptParam = (department == null || department.isBlank()) ? null : department.trim();

        return employeeRepository.searchEmployees(nameParam, deptParam).stream()
                .map(this::mapToDTO)
                .toList();
    }

    public Optional<EmployeeDTO> updateEmployee(Long id, EmployeeUpdateRequest request) {
        return employeeRepository.findById(id).map(employee -> {
            if (request.getName() != null && !request.getName().isBlank()) {
                employee.setName(utilityService.formatString(request.getName()));
            }
            if (request.getEmail() != null && !request.getEmail().isBlank()) {
                employee.setEmail(request.getEmail().trim());
            }
            if (request.getDepartment() != null && !request.getDepartment().isBlank()) {
                Department department = departmentRepository.findByNameIgnoreCase(request.getDepartment().trim())
                        .orElseGet(() -> departmentRepository.save(new Department(request.getDepartment().trim())));
                employee.setDepartment(department);
            }
            Employee updated = employeeRepository.save(employee);
            return mapToDTO(updated);
        });
    }

    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setCode(employee.getCode());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        if (employee.getDepartment() != null) {
            dto.setDepartment(employee.getDepartment().getName());
        }
        return dto;
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
