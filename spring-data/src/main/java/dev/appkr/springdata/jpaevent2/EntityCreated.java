package dev.appkr.springdata.jpaevent2;

public class EntityCreated extends DomainEvent<Example> {

  public EntityCreated(Example entity) {
    super(entity);
  }
}
