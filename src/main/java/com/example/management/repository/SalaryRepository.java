package com.example.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.management.entity.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    //JPQL
       @Query("SELECT SUM(s.newSalary) FROM Salary s " +
           "WHERE s.effectiveDate = (SELECT MAX(s2.effectiveDate) FROM Salary s2 WHERE s2.employee.id = s.employee.id)")
    Double calculateTotalSalaryCost();

 
    @Query("SELECT s FROM Salary s WHERE s.employee.id = :employeeId")
    Optional<Salary> findSalaryByEmployeeId(@Param("employeeId") Long employeeId);
}
