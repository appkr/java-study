package dev.appkr.springdata.envers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FooDto {

  private long id;
  private String name;
  private BarDto bar;
}
