package dev.appkr.springdata.envers2.model;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
@Audited
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"persons"})
public class Address implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id @GeneratedValue
  private int id;
  private String streetName;
  private Integer houseNumber;
  private Integer flatNumber;
  @OneToMany(mappedBy = "address")
  private Set<Person> persons;

  public Address(String streetName, Integer houseNumber) {
    this.streetName = streetName;
    this.houseNumber = houseNumber;
  }
}
