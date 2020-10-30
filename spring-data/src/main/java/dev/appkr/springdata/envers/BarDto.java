package dev.appkr.springdata.envers;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class BarDto {

  private long id;
  private String name;
  private Set<FooDto> fooSet = new HashSet<>();
}
