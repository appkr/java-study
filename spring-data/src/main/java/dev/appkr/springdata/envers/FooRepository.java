package dev.appkr.springdata.envers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface FooRepository extends JpaRepository<Foo, Long>, RevisionRepository<Foo, Long, Long> {
}
