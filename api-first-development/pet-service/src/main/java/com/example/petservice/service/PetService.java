package com.example.petservice.service;

import com.example.petservice.api.model.ListPetResponseDto;
import com.example.petservice.api.model.PageDto;
import com.example.petservice.api.model.PetDto;
import com.example.petservice.domain.Pet;
import com.example.petservice.domain.repository.PetRepository;
import com.example.petservice.service.exception.NameConflictException;
import com.example.petservice.service.exception.ResourceNotFoundException;
import com.example.petservice.service.mapper.PetMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository repository;
    private final PetMapper mapper;

    public PetService(PetRepository repository, PetMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PetDto createPet(PetDto dto) {
        if (repository.existsByName(dto.getName())) {
            throw new NameConflictException();
        }

        final Pet entity = mapper.toEntity(dto);
        repository.save(entity);

        return mapper.toDto(entity);
    }

    public PetDto findById(Long petId) {
        final Optional<Pet> optional = repository.findById(petId);
        final Pet entity = optional.orElseThrow(ResourceNotFoundException::new);

        return mapper.toDto(entity);
    }

    public ListPetResponseDto findAll(Specification specs, Pageable pageable) {
        final Page<Pet> res = repository.findAll(specs, pageable);

        final List<PetDto> data = mapper.toDto(res.getContent());
        final PageDto page = new PageDto()
            .size(res.getSize())
            .number(res.getNumber() + 1)
            .totalElements(res.getTotalElements())
            .totalPages(res.getTotalPages());

        return new ListPetResponseDto()
            .data(data)
            .page(page);
    }
}
