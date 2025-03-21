package com.example.management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.management.dto.EmployeeDetailsDTO;
import com.example.management.repository.EmployeeRepository;
import com.example.management.service.impl.EmployeeService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceUnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testGetEmployeeDetailsById() {
        // Tạo mock dữ liệu bằng Anonymous Class
        EmployeeDetailsDTO mockDTO = new EmployeeDetailsDTO() {
            @Override
            public Long getId() {
                return 14L;
            }

            @Override
            public String getFullName() {
                return "John Doe";
            }

            @Override
            public String getEmail() {
                return "johndoe@example.com";
            }

            @Override
            public String getDeptName() {
                return "IT";
            }

            @Override
            public String getPositionName() {
                return "Java Developer";
            }

            @Override
            public Double getNewSalary() {
                return 5000.0;
            }
        };

        // Mock repository để trả về dữ liệu giả
        when(employeeRepository.findEmployeeDetailsById(14L)).thenReturn(mockDTO);

        // Gọi service method
        EmployeeDetailsDTO result = employeeService.getEmployeeDetailsById(14L);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(14L, result.getId());
        assertEquals("John Doe", result.getFullName());
        assertEquals("IT", result.getDeptName());
        assertEquals("Java Developer", result.getPositionName());
        assertEquals(5000.0, result.getNewSalary());

        // Kiểm tra repository được gọi đúng số lần
        verify(employeeRepository, times(1)).findEmployeeDetailsById(14L);
    }

}
