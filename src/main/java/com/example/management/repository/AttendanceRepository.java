package com.example.management.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.management.entity.Attendance;
import com.example.management.entity.attendanceinterface.LeaveRequestProjection;
import com.example.management.util.Status;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployee_IdAndDate(Long id, LocalDate date);

    // Native Query
    @Query(value = "SELECT COUNT(DISTINCT date) FROM attendance WHERE employee_id = :employeeId AND MONTH(date) = :month AND YEAR(date) = :year", nativeQuery = true)
    int countWorkingDays(@Param("employeeId") Long employeeId, @Param("month") int month, @Param("year") int year);

    // Native Query
    @Query(value = "SELECT SUM(TIMESTAMPDIFF(HOUR, check_in, check_out)) FROM attendance WHERE employee_id = :employeeId AND MONTH(date) = :month AND YEAR(date) = :year", nativeQuery = true)
    Integer calculateTotalWorkingHours(@Param("employeeId") Long employeeId, @Param("month") int month,
            @Param("year") int year);

    //Customizing the Result with Spring Data Projection
    @Query("""
                SELECT a.id AS id, e.name AS employeeName, a.status AS status
                FROM Attendance a
                JOIN a.employee e
                WHERE a.status = :status
            """)
    List<LeaveRequestProjection> findLeaveRequests(@Param("status") Status status);
}
