## JPA Exercise

https://www.youtube.com/watch?v=WfrSN9Z7MiA&list=PL9mhQYIlKEhfpMVndI23RwWTL9-VL-B7U

## [토크ON세미나] JPA 프로그래밍 기본기 다지기 6강 - JPA 내부구조
- 서비스 클래스에서 트랜잭션이 끝나면 영속성 컨텍스트는 준영속(detached) 상태가 된다. 
- 해서, 트랜잭션 밖에서 Lazy Loading을 하려하면 `LazyInitializationException`이 발생한다.
- 즉, 영속성 컨텍스트가 영속(managed) 상태일 때 미리 Lazy 선언된 필드를 접근해서 로딩해두어야 한다. 

## [토크ON세미나] JPA 프로그래밍 기본기 다지기 7강 - JPA 객체지향쿼리
- JPQL과 QueryDSL은 다르다
```java
String jpql = "select m from Member m where m.name like '%hello%'";
List<Member> members = em.createQuery(jpql, Member.class).getResultList();
```

- `getResultList()`, `getSingleResult()` 
- Param binding
```java
String jpql = "select m from Member m where m.name = :username";
em.createQuery(jpql, Member.class).setParameter("username", userName);
```

- Entity Projection
```java
String jpql = "select m.team from Member m";
String jpql = "select m.name, m.age from Member m";
String jpql = "select new package.MemberDto(m.name, m.age) from Member m";
```

- Join & Fetch Join
```java
String jpql = "select m from Member m join m.team t";

// 별칭 없음 주의
String jpql = "select m from Member m join fetch m.team where m.name like '%hello%'";
List<Member> members = em.createQuery(jpql, Member.class).getResultList();
for (Member m: members) {
    // Fetch Join으로 회원과 팀을 함께 조회했으므로, 지연 로딩 발생 안함
    System.out.println("username = " + m.getName() + ", " + "teamname = " + m.getTeam().getName());
}
```

- Pagination
```java
String jpql = "...";
List<Member> resultList = em.createQuery(jpql, Member.class)
    .setFirstResult(10)
    .setMaxResults(20)
    .getResultList();
```

- Named Query
```java
@Entity
@NamedQuery(name = "Member.findByUserName",
    query = "select m from Member m where m.name = :userName")
public class Member {...}

List<Member> members = em.createNamedQuery("Member.findByUserName", Member.class)
    .setParameter("userName", "Foo")
    .getResultList();
```

## [토크ON세미나] JPA 프로그래밍 기본기 다지기 8강 - Spring Data JPA와 QueryDSL 이해
- 인터페이스만 생성하면 JPA가 구현체를 만들어 줌
```java
public interface Repository extends JpaRepository<Member, Long> {}
```

- Paging
```java
public class MemberController {
    @GetMapping("member")
    public Page<Member> getMember() {
        PageRequest req = PageRequest.of(0, 10);
        return repository.findByUsername("foo", req);
    }
}
```
members?page=0&size=10&sort=name,desc
```java
@GetMapping("member")
String listMembers(Pageable pageable, Model model) {
    // Pageable에 쿼리 파라미터가 이미 셋팅되어 있음
}
```


- Repository의 함수가 사용할 JPQL 직접 선언 가능
```java
public interface MemberRepository extends JpsRepository<Member, Long> {
    @Query("select m from Member m where m.username = ?1")
    Page<Member> findByUsername(String username, Pageable pageable);
}
```

- Return Type
```java
Member findByUsername(String username);
List<Member> findByUsername(String username);
```

- Query DSL, 컴파일 타임에 JPQL로 변환
```java
// select m from Member m where m.age > 18;

JPAFactoryQuery query = JPAQueryFactory(em);
QMember m = QMember.member;
List<Member> members = query.selectFrom(m)
    .where(m.age.gt(18))
    .orderBy(m.name.desc())
    .fetch();
```

- EntityManager 주입
```java
@PersistenceContext
private EntityManager em;
```

- Query DSL을 이용한 동적 쿼리
```java
JPAFactoryQuery query = JPAQueryFactory(em);
QMember m = QMember.member;

BooleanBuilder builder = new BooleanBuilder();

String name = "member";
if (name != null) {
    builder.and(m.name.contains(name));
}

int age = 9;
if (age != null) {
    builder.and(m.age.gt(age));
}

List<Member> members = query
    .selectFrom(m)
    .where(builder)
    .fetch();
```

```java
return query.selectFrom(coupon)
    .where(
        coupon.type.eq(typeParam),
        isServiceable()
    )
    .fetch();

private BooleanExpression isServiceable() {
    return coupon.status.eq("LIVE")
      .and(marketing.viewCount.lt(marketing.maxCount));
}
```
