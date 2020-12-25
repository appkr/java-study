package dev.appkr.springdata.envers2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EnversExampleUpdater {

  private final PersonRepository personRepository;

  @Transactional
  public void run() {
    final Optional<Person> optional = personRepository.findById(1);
    optional.orElseThrow(() -> new EntityNotFoundException());
    Person person = optional.get();
    person.setSurname("Granger");
    person.getAddress().setHouseNumber(5);
  }
}
