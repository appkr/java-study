package springstudy.restquery.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import springstudy.restquery.domain.User;
import springstudy.restquery.domain.UserRepository;
import springstudy.restquery.infra.UserSpecification;
import springstudy.restquery.service.dto.SearchCriteria;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JpaSpecificationTest {

    @Autowired
    private UserRepository repository;
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
        UserSpecification spec = new UserSpecification(new SearchCriteria("lastName", ":", "Doe"));

        List<User> results = repository.findAll(spec);

        assertEquals(2, results.size());
        assertTrue(results.contains(john));
        assertTrue(results.contains(tom));
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfusers_thenCorrect() {
        UserSpecification spec1 = new UserSpecification(new SearchCriteria("firstName", ":", "John"));
        UserSpecification spec2 = new UserSpecification(new SearchCriteria("lastName", ":", "Doe"));

        List<User> results = repository.findAll(Specification.where(spec1).and(spec2));

        assertTrue(results.contains(john));
        assertFalse(results.contains(tom));
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
        UserSpecification spec1 = new UserSpecification(new SearchCriteria("age", ">", 25));
        UserSpecification spec2 = new UserSpecification(new SearchCriteria("lastName", ":", "Doe"));

        List<User> results = repository.findAll(Specification.where(spec1).and(spec2));

        assertFalse(results.contains(john));
        assertTrue(results.contains(tom));
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        UserSpecification spec1 = new UserSpecification(new SearchCriteria("firstName", ":", "Adam"));
        UserSpecification spec2 = new UserSpecification(new SearchCriteria("lastName", ":", "Fox"));

        List<User> results = repository.findAll(Specification.where(spec1).and(spec2));

        assertTrue(results.isEmpty());
    }

    @Test
    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
        UserSpecification spec = new UserSpecification(new SearchCriteria("firstName", ":", "Jo"));

        List<User> results = repository.findAll(spec);

        assertTrue(results.contains(john));
        assertFalse(results.contains(tom));
    }
}
