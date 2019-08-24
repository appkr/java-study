package com.example.petservice.api;

import com.example.petservice.api.model.ListPetResponseDto;
import com.example.petservice.api.model.PetDto;
import com.example.petservice.domain.Pet;
import com.example.petservice.domain.PetType;
import com.example.petservice.service.PetService;
import com.example.petservice.service.dto.SearchCriteria;
import com.example.petservice.service.specification.PetSpecificationBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class PetApiDelegateImpl implements PetApiDelegate {

    private final Integer DEFAULT_PAGE = 0;
    private final Integer DEFAULT_SIZE = 20;

    private final PetService service;

    public PetApiDelegateImpl(PetService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Void> createPet(PetDto dto) {
        dto = service.createPet(dto);

        return ResponseEntity.created(URI.create("/api/pets/" + dto.getPetId())).build();
    }

    @Override
    public ResponseEntity<PetDto> getPet(Long petId) {
        PetDto dto = service.findById(petId);

        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<ListPetResponseDto> listPets(Integer page,
                                                       Integer size,
                                                       String petType,
                                                       String name) {
        final PetSpecificationBuilder builder = new PetSpecificationBuilder();
        if (petType != null) {
            builder.with("petType", PetType.valueOf(petType.toString()));
        }
        if (name != null) {
            builder.with("name", name);
        }
        final Specification<Pet> specs = builder.build();

        page = (page == null) ? DEFAULT_PAGE : page - 1;
        size = (size == null) ? DEFAULT_SIZE : size;
        final Pageable pageable = PageRequest.of(page, size);

        final ListPetResponseDto body = service.findAll(specs, pageable);

        return ResponseEntity.ok().body(body);
    }
}