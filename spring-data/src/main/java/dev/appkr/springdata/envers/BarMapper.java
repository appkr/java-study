package dev.appkr.springdata.envers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BarMapper {

//  ┌─────┐
//  |  barMapper defined in file [/Users/juwon.kim/workspace/java-study/spring-data/build/classes/java/main/dev/appkr/springdata/envers/BarMapper.class]
//  ↑     ↓
//  |  fooMapper defined in file [/Users/juwon.kim/workspace/java-study/spring-data/build/classes/java/main/dev/appkr/springdata/envers/FooMapper.class]
//  └─────┘
//  private final FooMapper fooMapper;

  public Bar toEntity(BarDto dto) {
    Bar bar = new Bar(dto.getName());
//    dto.getFooSet().forEach(fooDto -> {
//      bar.addFoo(fooMapper.toEntity(fooDto));
//    });

    return bar;
  }

  public BarDto toDto(Bar entity) {
    BarDto dto = new BarDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());

    return dto;
  }
}
