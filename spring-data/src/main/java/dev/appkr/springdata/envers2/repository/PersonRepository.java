package dev.appkr.springdata.envers2.repository;

import dev.appkr.springdata.envers2.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface PersonRepository extends RevisionRepository<Person, Integer, Integer>, JpaRepository<Person, Integer> {
}
