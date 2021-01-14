## Spring Data Examples

### JPA Entity Lifecycle Events
> There are two approaches for using the lifecycle event annotations
> - annotating methods in the entity
> - creating an EntityListener with annotated callback methods

So, if we create a new entity and call the save method of our repository, our method annotated with `@PrePersist` is called, then the record is inserted into the database, and finally, our `@PostPersist` method is called. If we're using `@GeneratedValue` to automatically generate our primary keys, we can expect that key to be available in the `@PostPersist` method.

For the `@PostPersist`, `@PostRemove` and `@PostUpdate` operations, the documentation mentions that these events can happen right after the operation occurs, after a flush, or at the end of a transaction.

#### References
- https://www.baeldung.com/jpa-entity-lifecycle-events
- dev.appkr.springdata.jpaevent

**We should note that the `@PreUpdate` callback is only called if the data is actually changed — that is if there's an actual SQL update statement to run. The `@PostUpdate` callback is called regardless of whether anything actually changed**.

---

### Hibernate Envers

```
Foo ---> Bar
Bar <>-> Foo
```

#### References
- https://www.baeldung.com/database-auditing-jpa#hibernate
- https://github.com/spring-projects/spring-data-envers/tree/master/src/test/java/org/springframework/data/envers
- dev.appkr.springdata.envers

---

### Hibernate Envers - Easy Entity Auditing

```
Person ---> Address
Address <>-> Person
```

<details>
<summary>Spring Log</summary>

```bash
$ export SERVER_PORT=8081;SPRING_PROFILES_ACTIVE=local ./gradlew clean bootRun
# 2020-11-03 20:41:46.468  INFO 65102 --- [           main] d.a.s.envers2.EnversExampleReader        : Person revision DefaultRevisionMetadata{entity=DefaultRevisionEntity(id = 3, revisionDate = 2020. 11. 3. 오후 8:41:46), revisionType=INSERT} Person(id=1, name=Harry, surname=Potter, address=Address(id=2, streetName=Privet Drive, houseNumber=4, flatNumber=null))
# 2020-11-03 20:41:46.482  INFO 65102 --- [           main] d.a.s.envers2.EnversExampleReader        : Person revision DefaultRevisionMetadata{entity=DefaultRevisionEntity(id = 4, revisionDate = 2020. 11. 3. 오후 8:41:46), revisionType=UPDATE} Person(id=1, name=Harry, surname=Granger, address=Address(id=2, streetName=Privet Drive, houseNumber=5, flatNumber=null))
# 2020-11-03 20:41:46.486  INFO 65102 --- [           main] d.a.s.envers2.EnversExampleReader        : Person revision DefaultRevisionMetadata{entity=DefaultRevisionEntity(id = 5, revisionDate = 2020. 11. 3. 오후 8:41:46), revisionType=DELETE} Person(id=1, name=null, surname=null, address=null)
```

</details>

<details>
<summary>SQL</summary>

```sql
select
    p.*,
    a.* 
from
    person_revisions p cross 
join
    audits a 
where
    p.id=? 
    and p.revision=a.id 
order by
    p.revision asc

--

select
    a1.*
from
    address_revisions a1 
where
    a1.revision=(
        select
            max(a2.revision) 
        from
            address_revisions a2 
        where
            a2.revision<=? 
            and a1.id=a2.id
    ) 
    and a1.revision_type<>? 
    and a1.id=?
```

</details>

#### Extension points
- org.hibernate.envers.DefaultRevisionEntity
- org.jboss.envers.RevisionListener

#### References
- https://docs.jboss.org/envers/docs/
- dev.appkr.springdata.envers2

---

### java-object-diff

> **`CAVEAT`**
> 
> which requires your objects **to provide getters** and setters for their properties. However, you don't need to provide setters, if you don't need write access to the properties
> 
> @see https://github.com/SQiShER/java-object-diff#caveats
