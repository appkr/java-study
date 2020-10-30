package dev.appkr.springdata.envers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FooService {

  private final FooRepository repository;

  @Transactional
  public Foo createFoo(Foo foo) {
    return repository.save(foo);
  }

  @Transactional(readOnly = true)
  public Optional<Foo> getFoo(Long id) {
    return repository.findById(id);
  }
}
