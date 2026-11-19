package com.example.employeemanagement.repository;

import com.example.employeemanagement.dto.DepartmentStatisticDTO;
import com.example.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByCode(String code);

    @Query("SELECT e FROM Employee e LEFT JOIN e.department d WHERE (:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND (:department IS NULL OR LOWER(d.name) = LOWER(:department))")
    List<Employee> searchEmployees(@Param("name") String name, @Param("department") String department);

    @Query("SELECT new com.example.employeemanagement.dto.DepartmentStatisticDTO(COALESCE(d.name, 'Unassigned'), COUNT(e)) FROM Employee e LEFT JOIN e.department d GROUP BY d.name")
    List<DepartmentStatisticDTO> countEmployeesByDepartment();

    @Query("SELECT COUNT(e) FROM Employee e")
    long countTotalEmployees();
}
