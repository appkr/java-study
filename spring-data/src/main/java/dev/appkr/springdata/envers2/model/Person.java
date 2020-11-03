package dev.appkr.springdata.envers2.model;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Audited
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
public class Person implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id @GeneratedValue
  private int id;
  private String name;
  private String surname;
  @ManyToOne(cascade = CascadeType.ALL)
  private Address address;

  public Person(String name, String surname, Address address) {
    this.name = name;
    this.surname = surname;
    this.address = address;
  }
}
