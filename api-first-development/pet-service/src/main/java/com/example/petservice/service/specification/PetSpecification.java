package com.example.petservice.service.specification;

import com.example.petservice.domain.Pet;
import com.example.petservice.service.dto.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PetSpecification implements Specification<Pet> {

    private final SearchCriteria params;

    public PetSpecification(SearchCriteria params) {
        this.params = params;
    }

    @Override
    public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (root.get(params.getKey()).getJavaType() == String.class) {
            return builder.like(builder.lower(root.<String>get(params.getKey())),
                "%" + params.getValue().toString().toLowerCase() + "%");
        } else {
            return builder.equal(root.get(params.getKey()), params.getValue());
        }
    }
}
