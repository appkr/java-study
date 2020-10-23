package dev.appkr.springdata.converter;

import lombok.*;
import org.springframework.data.geo.Point;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Convert(converter = PointConverter.class)
  @Lob
  private Point location;

  public User(String name, Point location) {
    this.name = name;
    this.location = location;
  }
}
