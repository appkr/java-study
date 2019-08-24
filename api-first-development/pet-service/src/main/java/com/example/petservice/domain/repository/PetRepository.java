package com.example.petservice.domain.repository;

import com.example.petservice.domain.Pet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PetRepository extends PagingAndSortingRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {

    boolean existsByName(String name);
}
