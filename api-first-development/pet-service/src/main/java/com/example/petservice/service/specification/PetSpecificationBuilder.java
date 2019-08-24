package com.example.petservice.service.specification;

import com.example.petservice.domain.Pet;
import com.example.petservice.service.dto.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PetSpecificationBuilder {

    private final List<SearchCriteria> params;

    public PetSpecificationBuilder() {
        this.params = new ArrayList<SearchCriteria>();
    }

    public PetSpecificationBuilder with(String key, Object value) {
        params.add(new SearchCriteria(key, value));
        return this;
    }

    public Specification<Pet> build() {
        if (params.size() == 0) {
            return null;
        }

        final List<PetSpecification> specs = params.stream()
            .map(PetSpecification::new)
            .collect(toList());

        Specification result = specs.get(0);
        for (int i = 0; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }
}
