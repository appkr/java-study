package dev.appkr.springdata.envers2.repository;

import dev.appkr.springdata.envers2.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface AddressRepository extends RevisionRepository<Address, Integer, Integer>, JpaRepository<Address, Integer> {
}
