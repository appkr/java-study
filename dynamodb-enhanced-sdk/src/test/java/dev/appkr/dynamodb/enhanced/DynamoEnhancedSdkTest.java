package dev.appkr.dynamodb.enhanced;

import dev.appkr.dynamodb.model.Customer;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DynamoEnhancedSdkTest {

  @Autowired CustomerRepository repository;

  @BeforeEach
  void setup() {
    repository.createTableIfNotExists().join();

    final Customer item = new Customer();
    item.setId("foo");
    item.setEmail("foo@bar.com");
    item.setName("Foo");
    item.setRegDate(Instant.now());

    repository.save(item).join();
  }

  @AfterEach
  void teardown() {
    repository.deleteTableIfExists().join();
  }

  @Test
  @Disabled
  void create() {
    final Customer item = new Customer();
    item.setId("foo");
    item.setEmail("foo@bar.com");
    item.setName("Foo");
    item.setRegDate(Instant.now());

    final CompletableFuture<Customer> future = repository.save(item);

    log.info("{}", future.join());
  }

  @Test
  void findById() {
    final CompletableFuture<Customer> future = repository.findById("foo");

    log.info("{}", future.join());
  }

  @Test
  void update() {
    final CompletableFuture<Customer> future = repository.findById("foo")
        .thenCompose(item -> {
          item.setName("Bar");
          return repository.save(item);
        })
        .thenCompose(item -> repository.findById(item.getId()));

    log.info("{}", future.join());
  }

  @Test
  void findAll() {
    repository.findAll()
        .subscribe(item -> log.info("{}", item))
        .join();
  }

  @Test
  void deleteItem() {
    final CompletableFuture<Customer> future = repository.findById("foo")
        .thenCompose(repository::delete);

    log.info("{}", future.join());
  }
}
