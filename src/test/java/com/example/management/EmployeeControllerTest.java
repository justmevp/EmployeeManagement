package com.example.management;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.management.controller.EmployeeController;
import com.example.management.dto.EmployeeDetailsDTO;
import com.example.management.service.impl.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testGetEmployeeDetails() throws Exception {
        // Tạo object thực thay vì mock
        EmployeeDetailsDTO mockDTO = new EmployeeDetailsDTO() {
            @Override public Long getId() { return 14L; }
            @Override public String getFullName() { return "John Doe"; }
            @Override public String getEmail() { return "johndoe@example.com"; }
            @Override public String getDeptName() { return "IT"; }
            @Override public String getPositionName() { return "Java Developer"; }
            @Override public Double getNewSalary() { return 5000.0; }
        };

        when(employeeService.getEmployeeDetailsById(14L)).thenReturn(mockDTO);

        mockMvc.perform(get("/api/employees/14")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(14))
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"))
                .andExpect(jsonPath("$.deptName").value("IT"))
                .andExpect(jsonPath("$.positionName").value("Java Developer"))
                .andExpect(jsonPath("$.newSalary").value(5000.0));

        verify(employeeService, times(1)).getEmployeeDetailsById(14L);
    }
}



