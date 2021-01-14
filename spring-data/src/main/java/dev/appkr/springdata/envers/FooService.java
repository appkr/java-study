package dev.appkr.springdata.envers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.history.Revision;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FooService {

  private final FooRepository repository;
  private final FooMapper mapper;

  @Transactional
  public FooDto createFoo(FooDto dto) {
    Foo entity = repository.save(mapper.toEntity(dto));

    return mapper.toDto(entity);
  }

  @Transactional
  public void updateFoo(Long id, FooDto dto) {
    Optional<Foo> optional = repository.findById(id);
    optional.orElseThrow(() -> new EntityNotFoundException());

    Foo entity = optional.get();
    if (dto.getName() != null) {
      entity.setName(dto.getName());
    }
  }

  @Transactional(readOnly = true)
  public FooDto getFoo(Long id) {
    Optional<Foo> optional = repository.findById(id);
    Foo entity = optional.orElseThrow(() -> new EntityNotFoundException());

    return mapper.toDto(entity);
  }

  @Transactional(readOnly = true)
  public FooDto getLatestRevision(Long id) {
    Optional<Revision<Long, Foo>> optional = repository.findLastChangeRevision(id);
    if (optional.isPresent()) {
      final Foo entity = optional.get().getEntity();
      return mapper.toDto(entity);
    }

    return new FooDto();
  }
}
