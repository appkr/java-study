package dev.appkr.dynamodb.documentPoc;

import dev.appkr.dynamodb.model.Address;
import dev.appkr.dynamodb.model.Contact;
import dev.appkr.dynamodb.model.Contact.Type;
import dev.appkr.dynamodb.model.User;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
@Slf4j
class UserRepositoryTest {

  @Autowired UserRepository repository;
  User user;

  @BeforeEach
  void setup() {
    this.user = new User();
    user.setId("foo");
    user.setName("Foo");
    user.setAddress(new Address("Seoul", "Gangnamgu"));
    user.setContacts(List.of(new Contact(Type.EMAIL, "foo@example.com"), new Contact(Type.PHONE, "000-0000-0000")));
    user.setRegisteredAt(Instant.now());

    repository.createTableIfNotExists()
        .flatMap(v -> repository.save(this.user))
        .block();
  }


  @AfterEach
  void teardown() {
    repository.deleteTableIfExists().block();
  }

  @Test
  void findAll() {
    repository.findAll()
        .log()
        .as(StepVerifier::create)
        .verifyComplete();
  }

  @Test
  void findById() {
    repository.findById("foo")
        .log()
        .as(StepVerifier::create)
        .verifyComplete();
  }

  @Test
  void delete() {
    repository.delete(user)
        .log()
        .as(StepVerifier::create)
        .verifyComplete();
  }
}
