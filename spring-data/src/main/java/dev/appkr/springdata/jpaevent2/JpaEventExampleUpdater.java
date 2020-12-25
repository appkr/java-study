package dev.appkr.springdata.jpaevent2;

import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class JpaEventExampleUpdater implements CommandLineRunner {

  private final ExampleRepository repository;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    Example original = Example.of("original title");
    repository.saveAndFlush(original);

    final Optional<Example> optional = repository.findById(1);
    Example changed = optional.orElseThrow(() -> new NoSuchElementException());
    changed.changeTitle("changed title");
    // IMPORTANT NOTE
    // If not explicitly call save(), the event will not fire
    // @see https://www.baeldung.com/spring-data-ddd#1-unpublished-events
    repository.saveAndFlush(changed);
  }
}
