package com.example.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.management.dto.DepartmentDTO;
import com.example.management.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {


    //Customizing the Result with Class Constructors
    @Query("SELECT new com.example.management.dto.DepartmentDTO(d.name, COUNT(e.id)) " +
            "FROM Department d LEFT JOIN d.employees e " +
            "GROUP BY d.name")
    List<DepartmentDTO> getEmployeeCountByDepartment();
}
