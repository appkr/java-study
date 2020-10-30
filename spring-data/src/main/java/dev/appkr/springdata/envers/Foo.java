package dev.appkr.springdata.envers;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Audited
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
public class Foo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;

  @ManyToOne(targetEntity = Bar.class, cascade = CascadeType.ALL)
  private Bar bar = new Bar();

  public Foo(String name) {
    this.name = name;
  }

  public Foo() {}

  public void changeBarName(String name) {
    this.bar.setName(name);
  }
}
