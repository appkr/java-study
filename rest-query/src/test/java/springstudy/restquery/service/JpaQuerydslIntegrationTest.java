package springstudy.restquery.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springstudy.restquery.domain.Ex3UserRepository;
import springstudy.restquery.domain.User;
import springstudy.restquery.infra.UserPredicateBuilder;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JpaQuerydslIntegrationTest {

    @Autowired
    private Ex3UserRepository repository;
    private User john;
    private User tom;

    @Before
    public void setUp() {
        john = new User();
        john.setFirstName("John");
        john.setLastName("Doe");
        john.setEmail("john@example.com");
        john.setAge(22);
        repository.save(john);

        tom = new User();
        tom.setFirstName("Tom");
        tom.setLastName("Doe");
        tom.setEmail("tom@example.com");
        tom.setAge(26);
        repository.save(tom);
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        UserPredicateBuilder builder = new UserPredicateBuilder();
        BooleanExpression predicate = builder.with("lastName", ":", "Doe").build();

        Iterable<User> results = repository.findAll(predicate);
        List<User> list = iterableToList(results);
        
        assertTrue(list.contains(john));
        assertTrue(list.contains(tom));
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        UserPredicateBuilder builder = new UserPredicateBuilder();
        BooleanExpression predicate = builder
            .with("firstName", ":", "John")
            .with("lastName", ":", "Doe")
            .build();

        Iterable<User> results = repository.findAll(predicate);
        List<User> list = iterableToList(results);

        assertTrue(list.contains(john));
        assertFalse(list.contains(tom));
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
        UserPredicateBuilder builder = new UserPredicateBuilder();
        BooleanExpression predicate = builder
            .with("lastName", ":", "Doe")
            .with("age", ">", 25)
            .build();

        Iterable<User> results = repository.findAll(predicate);
        List<User> list = iterableToList(results);

        assertFalse(list.contains(john));
        assertTrue(list.contains(tom));
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        UserPredicateBuilder builder = new UserPredicateBuilder();
        BooleanExpression predicate = builder
            .with("firstName", ":", "Adam")
            .with("lastName", ":", "Fox")
            .build();

        Iterable<User> results = repository.findAll(predicate);
        List<User> list = iterableToList(results);

        assertTrue(list.isEmpty());
    }

    @Test
    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
        UserPredicateBuilder builder = new UserPredicateBuilder();
        BooleanExpression predicate = builder.with("firstName", ":", "Jo").build();

        Iterable<User> results = repository.findAll(predicate);
        List<User> list = iterableToList(results);

        assertTrue(list.contains(john));
    }

    private List<User> iterableToList(Iterable<User> iter) {
        List<User> list = new ArrayList<>();
        iter.forEach(list::add);
        return list;
    }
}
