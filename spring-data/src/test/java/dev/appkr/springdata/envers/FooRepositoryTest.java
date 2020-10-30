package dev.appkr.springdata.envers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.history.Revision;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class FooRepositoryTest {

  @Autowired
  private FooRepository repository;

  private Foo foo;

  @BeforeEach
  @Transactional
  public void setUp() {
    Foo foo = new Foo("foo");
    Bar bar = new Bar("bar");
    foo.setBar(bar);
    bar.addFoo(foo);

    this.foo = repository.saveAndFlush(foo);
  }

  @Test
  public void testLifecycle() {
    // then
    log.info("Foo & Bar objects {}", foo);
    assertFalse(repository.findAll().isEmpty());
  }

  @Test
  public void testUpdate() throws InterruptedException {
    // given
    foo.setName("changedFoo");
    foo.changeBarName("changedBar");

    // when
    repository.saveAndFlush(foo);
    final Optional<Revision<Long, Foo>> revision = repository.findLastChangeRevision(foo.getId());
    final Page<Revision<Long, Foo>> page = repository.findRevisions(foo.getId(), PageRequest.of(0, 10));

    // then
    revision.ifPresent(r -> {
      log.info("Revision entity {}", r.getEntity());
      log.info("Revision metadata {}", r.getMetadata());
    });
  }
}