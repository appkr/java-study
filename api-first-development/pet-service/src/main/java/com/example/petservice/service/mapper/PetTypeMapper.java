package com.example.petservice.service.mapper;

import com.example.petservice.api.model.PetTypeDto;
import com.example.petservice.domain.PetType;
import org.springframework.stereotype.Component;

@Component
public class PetTypeMapper {

    public PetType toEntity(PetTypeDto dto) {
        if (dto == null) {
            return null;
        }

        return PetType.valueOf(dto.toString());
    }

    public PetTypeDto toDto(PetType entity) {
        if (entity == null) {
            return null;
        }

        return PetTypeDto.fromValue(entity.toString());
    }
}
