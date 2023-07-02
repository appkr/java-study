package dev.appkr.dynamodb.tuplePoc;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

import dev.appkr.dynamodb.model.ChangeLog;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class ChangeLogJsonRepositoryTest {

  @Autowired ChangeLogJsonRepository repository;

  @BeforeEach
  void setup() {
    final ChangeLog changeLog1 = new ChangeLog(randomUUID().toString(), "type-a", "complex fields 1");
    final ChangeLog changeLog2 = new ChangeLog(randomUUID().toString(), "type-a", "complex fields 2");
    final ChangeLog changeLog3 = new ChangeLog(randomUUID().toString(), "type-b", "complex fields 3");

    repository.createTableIfNotExists().block();
    Flux.fromStream(Stream.of(changeLog1, changeLog2, changeLog3))
        .flatMap(repository::save)
        .blockLast();
  }

  @AfterEach
  void teardown() {
    repository.deleteTableIfExists().block();
  }

  @Test
  void findAllByType() {
    repository.findAllByType("type-a")
        .log()
        .as(StepVerifier::create)
        .assertNext(l -> assertThat(l.size()).isEqualTo(2))
        .verifyComplete();

    repository.findAllByType("type-b")
        .log()
        .as(StepVerifier::create)
        .assertNext(l -> assertThat(l.size()).isEqualTo(1))
        .verifyComplete();
  }
}