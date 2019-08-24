package com.example.petservice.service.mapper;

import com.example.petservice.api.model.PetDto;
import com.example.petservice.api.model.PetTypeDto;
import com.example.petservice.domain.Pet;
import com.example.petservice.domain.PetType;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PetMapper {

    private PetTypeMapper petTypeMapper;
    private DateTimeMapper dateTimeMapper;

    public PetMapper(PetTypeMapper petTypeMapper, DateTimeMapper dateTimeMapper) {
        this.petTypeMapper = petTypeMapper;
        this.dateTimeMapper = dateTimeMapper;
    }

    public Pet toEntity(PetDto dto) {
        if (dto == null) {
            return null;
        }

        final PetType petType = petTypeMapper.toEntity(dto.getPetType());
        return Pet.of(dto.getName(), petType);
    }

    public PetDto toDto(Pet entity) {
        if (entity == null) {
            return null;
        }

        final PetTypeDto petTypeDTO = petTypeMapper.toDto(entity.getPetType());
        final OffsetDateTime createdAt = dateTimeMapper.toOffsetDateTime(entity.getCreatedAt());
        final OffsetDateTime updatedAt = dateTimeMapper.toOffsetDateTime(entity.getUpdatedAt());

        return new PetDto()
            .petId(entity.getId())
            .name(entity.getName())
            .petType(petTypeDTO)
            .createdAt(createdAt)
            .updatedAt(updatedAt);
    }

    public List<PetDto> toDto(List<Pet> petList) {
        if (petList == null) {
            return null;
        }

        final List<PetDto> list = new ArrayList<>(petList.size());
        for (Pet entity : petList) {
            list.add(toDto(entity));
        }

        return list;
    }
}
