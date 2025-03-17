package com.example.management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.management.dto.PositionDTO;
import com.example.management.entity.Position;


@Mapper
public interface PositionMapper {

    PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);
    PositionDTO toDto(Position position);
    Position toEntity(PositionDTO positionDTO);
    
}
