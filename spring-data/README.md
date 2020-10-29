## Spring Data Examples

- [JPA Entity Lifecycle Events](https://www.baeldung.com/jpa-entity-lifecycle-events)
> There are two approaches for using the lifecycle event annotations
> - annotating methods in the entity
> - creating an EntityListener with annotated callback methods

So, if we create a new entity and call the save method of our repository, our method annotated with `@PrePersist` is called, then the record is inserted into the database, and finally, our `@PostPersist` method is called. If we're using `@GeneratedValue` to automatically generate our primary keys, we can expect that key to be available in the `@PostPersist` method.

For the `@PostPersist`, `@PostRemove` and `@PostUpdate` operations, the documentation mentions that these events can happen right after the operation occurs, after a flush, or at the end of a transaction.

**We should note that the `@PreUpdate` callback is only called if the data is actually changed — that is if there's an actual SQL update statement to run. The `@PostUpdate` callback is called regardless of whether anything actually changed**.