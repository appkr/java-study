package dev.appkr.springdata.jpaevent2;

public abstract class DomainEvent<T> {

  private T entity;
  private final long timestamp;

  public DomainEvent(T entity) {
    this.entity = entity;
    this.timestamp = System.currentTimeMillis();
  }

  public T getEntity() {
    return entity;
  }

  @Override
  public String toString() {
    return "DomainEvent{" +
        "entity=" + entity +
        ", timestamp=" + timestamp +
        '}';
  }
}
