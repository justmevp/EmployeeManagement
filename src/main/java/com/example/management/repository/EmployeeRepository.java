package com.example.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.management.dto.EmployeeDTO;
import com.example.management.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);

    Optional<Employee> findByEmail(String email);

    // Join table
    @Query("SELECT e FROM Employee e WHERE e.position.name = :positionName")
    List<Employee> getEmployeesByPosition(@Param("positionName") String positionName);

    @Query("SELECT new com.example.management.dto.EmployeeDTO(e.id, e.name, s.newSalary) " +
            "FROM Employee e JOIN Salary s ON e.id = s.employee.id " +
            "ORDER BY s.newSalary DESC")
    Page<EmployeeDTO> findEmployeesWithSalary(Pageable pageable);

     // Slice Query
     @Query("SELECT new com.example.management.dto.EmployeeDTO(e.id, e.name, s.newSalary) " +
     "FROM Employee e JOIN Salary s ON e.id = s.employee.id " +
     "ORDER BY s.newSalary DESC")
    Slice<EmployeeDTO> findEmployeesWithSalarySlice(Pageable pageable);

}
