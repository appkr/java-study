package dev.appkr.springdata.envers2;

import dev.appkr.springdata.envers2.model.Address;
import dev.appkr.springdata.envers2.model.Person;
import dev.appkr.springdata.envers2.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnversExampleCreator {

  private final PersonRepository personRepository;

  @Transactional
  public void run() {
    Address address1 = new Address("Privet Drive", 4);
    Person person1 = new Person("Harry", "Potter", address1);

    personRepository.save(person1);
    log.info("Person object {}", person1);
  }
}
