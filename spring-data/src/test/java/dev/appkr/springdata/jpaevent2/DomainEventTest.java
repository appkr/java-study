package dev.appkr.springdata.jpaevent2;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DomainEventTest {

  @Autowired
  private ExampleRepository repository;

  @MockBean
  private DomainEventListener eventListener;

  @DisplayName("given an entity extending AbstractAggregateRoot,"
      + " when do domain operation and save,"
      + " then an event is published")
  @Test
//  With @Transactional on test method, the transaction is going to be rolled-back after the test execution
//  So, @TransactionalEventListener will not be invoked; To avoid this, @Transactional was commented out.
//  @Transactional
  public void testEntityCreation() {
    Example example = Example.of("original title");
    repository.save(example);

    verify(eventListener).handle(any(DomainEvent.class));
  }

  @DisplayName("given an entity extending AbstractAggregateRoot,"
      + " when change the entity,"
      + " then an event is published")
  @Test
//  @Transactional
  public void testEntityMutation() {
    Example example = Example.of("original title");
    repository.save(example);

    example.changeTitle("changed title");
    // IMPORTANT NOTE
    // If not explicitly call save(), the event will not fire
    // @see https://www.baeldung.com/spring-data-ddd#1-unpublished-events
    repository.saveAndFlush(example);

    verify(eventListener, times(2)).handle(any(DomainEvent.class));
  }
}
