package dev.appkr.springdata.envers2;

import dev.appkr.springdata.envers2.model.Person;
import dev.appkr.springdata.envers2.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EnversExampleDeleter {

  private final PersonRepository personRepository;

  @Transactional
  public void run() {
    final Optional<Person> optional = personRepository.findById(1);
    optional.orElseThrow(() -> new EntityNotFoundException());
    Person person = optional.get();
    personRepository.delete(person);
  }
}
