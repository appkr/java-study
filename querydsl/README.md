## QueryDSL

- http://www.querydsl.com/
- http://www.querydsl.com/static/querydsl/4.0.1/reference/ko-KR/html_single/

#### Setup
```groovy
// build.gradle

sourceSets {
    main {
        java {
            srcDirs += ["build/generated/sources/annotationProcessor/java/main"]
        }
    }
}

ext {
    querydslVersion = '4.2.1'
    hibernateJpaApiVersion = '1.0.2.Final'
}

dependencies {
    compile "com.querydsl:querydsl-jpa:${querydslVersion}"
    compile "com.querydsl:querydsl-core:${querydslVersion}"
    compileOnly "com.querydsl:querydsl-apt:${querydslVersion}"
    annotationProcessor "com.querydsl:querydsl-apt:${querydslVersion}:jpa"
    annotationProcessor "org.hibernate.javax.persistence:hibernate-jpa-2.1-api:${hibernateJpaApiVersion}"
}
```

```java
// QuerydslConfiguration.java
@Configuration
public class QuerydslConfiguration {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
```

#### User

Create domain entity and then...

```java
// CustomerRepository.java
@Repository
interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {
}
```

```java
// CustomerRepositoryCustom.java
public interface CustomerRepositoryCustom { }

// CustomerRepositoryImpl.java
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public CustomerRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
}
```

#### BooleanBuilder

```
+----------------------------------+
| <<I>> Expression                 |
+----------------------------------+
| + accept(Visitor<R,C>, C): R     |
+----------------------------------+
                  ↑
+----------------------------------+
| <<I>> Predicate                  |
+----------------------------------+
| + not(): Predicate               |
+----------------------------------+
                  ↑
+----------------------------------+
| <<I>> BooleanBuilder             |
+----------------------------------+
| + @Override accept()             |
| + @Override not()                |
| + and(Predicate): self           |
| + andAnyOf(...Predicate): self   |
| + andNot(Predicate): self        |
| + or(Predicate): self            |
| + orAllOf(...Predicate): self    |
| + orNot(Predicate): self         |
+----------------------------------+
```
