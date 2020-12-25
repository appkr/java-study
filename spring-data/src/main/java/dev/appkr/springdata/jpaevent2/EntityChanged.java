package dev.appkr.springdata.jpaevent2;

public class EntityChanged extends DomainEvent<Example> {

  public EntityChanged(Example entity) {
    super(entity);
  }
}
