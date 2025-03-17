package com.example.management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.management.dto.AttendanceDTO;
import com.example.management.entity.Attendance;


@Mapper
public interface AttendanceMapper {

    AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);
    AttendanceDTO toDto(Attendance attendance);
    Attendance toEntity(AttendanceDTO attendanceDTO);
    
}
