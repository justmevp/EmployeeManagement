package com.example.management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.management.dto.EmployeeDTO;
import com.example.management.entity.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // Nếu sử dụng componentModel = "spring", bạn có thể inject mapper vào service thay vì dùng biến tĩnh.
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    // Ánh xạ từ Employee sang EmployeeDTO:
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "position.id", target = "positionId")
    EmployeeDTO toDto(Employee employee);

    // Ánh xạ từ EmployeeDTO sang Employee:
    // Chúng ta ignore các thuộc tính quan hệ (department, position) vì chúng cần được set riêng trong service.
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "createdBy", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);
}
