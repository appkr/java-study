package dev.appkr.springdata.envers2;

import dev.appkr.springdata.envers2.model.Person;
import dev.appkr.springdata.envers2.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnversExampleReader {

  private final PersonRepository personRepository;

  @Transactional(readOnly = true)
  public void read(Integer id) {
    final Revisions<Integer, Person> revisions = personRepository.findRevisions(id);
    revisions.forEach(r -> {
      log.info("Person revision {} {}", r.getMetadata(), r.getEntity());
    });
  }
}
