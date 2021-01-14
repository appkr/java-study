package dev.appkr.springdata.envers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FooMapper {

  private final BarMapper barMapper;

  public Foo toEntity(FooDto dto) {
    Foo entity = new Foo(dto.getName());
    if (dto.getBar() != null) {
      entity.setBar(barMapper.toEntity(dto.getBar()));
    }

    return entity;
  }

  public FooDto toDto(Foo entity) {
    FooDto dto = new FooDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    if (entity.getBar() != null) {
      dto.setBar(barMapper.toDto(entity.getBar()));
    }

    return dto;
  }
}
