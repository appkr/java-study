package dev.appkr.springdata.envers;

import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Audited
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"fooSet"})
public class Bar implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;

  @OneToMany(mappedBy = "bar", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @NotAudited
  private Set<Foo> fooSet = new HashSet<>();

  public Bar(String name) {
    this.name = name;
  }

  public Bar() { }

  public void addFoo(Foo foo) {
    this.fooSet.add(foo);
  }
}
