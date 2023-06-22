package dev.appkr.dynamodb.keyvaluePoc;

import dev.appkr.dynamodb.model.Address;
import dev.appkr.dynamodb.model.Contact;
import dev.appkr.dynamodb.model.Contact.Type;
import dev.appkr.dynamodb.model.User;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class UserJsonRepositoryTest {

  @Autowired UserJsonRepository repository;

  User user;

  @BeforeEach
  void setup() {
    final Address address = new Address("Seoul", "Gangnamgu");
    final List<Contact> contacts = List.of(
        new Contact(Type.EMAIL, "foo@example.com"),
        new Contact(Type.PHONE, "000-0000-0000")
    );

    this.user = new User();
    user.setId("foo");
    user.setName("Foo");
    user.setAddress(address);
    user.setContacts(contacts);
    user.setRegisteredAt(Instant.now());

    repository.createTableIfNotExists().block();
    repository.save(user).block();
  }

  @AfterEach
  void teardown() {
    repository.deleteTableIfExists().block();
  }

  @Test
  @Disabled
  void save() {
    repository.save(user)
        .log()
        .as(StepVerifier::create)
        .assertNext(Assertions::assertNotNull)
        .verifyComplete();
  }

  @Test
  void findById() {
    repository.findById(user.getId())
        .log()
        .as(StepVerifier::create)
        .assertNext(Assertions::assertNotNull)
        .verifyComplete();
  }

  @Test
  void findAll() {
    repository.findAll()
        .log()
        .as(StepVerifier::create)
        .expectNextCount(1L)
        .verifyComplete();
  }

  @Test
  void delete() {
    repository.delete(user)
        .log()
        .as(StepVerifier::create)
        .assertNext(Assertions::assertNotNull)
        .verifyComplete();
  }
}