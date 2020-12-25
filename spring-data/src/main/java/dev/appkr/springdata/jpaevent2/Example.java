package dev.appkr.springdata.jpaevent2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@Table(name = "examples")
@EqualsAndHashCode(of = {"id"})
@ToString
public class Example extends AbstractAggregateRoot<Example> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;

  protected Example() {
  }

  private Example(String title) {
    this.title = title;
  }

  public static Example of(String title) {
    Example example = new Example(title);
    example.registerEvent(new EntityCreated(example));
    return example;
  }

  public void changeTitle(String title) {
    this.title = title;
    this.registerEvent(new EntityChanged(this));
  }
}
