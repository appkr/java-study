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
import springstudy.restquery.infra.Ex4UserSpecification;
import springstudy.restquery.infra.SearchOperation;
import springstudy.restquery.service.dto.Ex4SearchCriteria;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class Ex4JpaSpecificationTest {

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
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        Specification spec1 = new Ex4UserSpecification(new Ex4SearchCriteria("firstName", SearchOperation.EQUALITY, "john"));
        Specification spec2 = new Ex4UserSpecification(new Ex4SearchCriteria("lastName", SearchOperation.EQUALITY, "doe"));

        List<User> results = repository.findAll(Specification.where(spec1).and(spec2));

        assertTrue(results.contains(john));
        assertFalse(results.contains(tom));
    }

    @Test
    public void givenFirstNameInverse_whenGettingListOfUsers_thenCorrect() {
        Specification spec = new Ex4UserSpecification(new Ex4SearchCriteria("firstName", SearchOperation.NEGATION, "john"));

        List<User> results = repository.findAll(spec);

        assertFalse(results.contains(john));
        assertTrue(results.contains(tom));
    }

    @Test
    public void givenMinAge_whenGettingListOfUsers_thenCorrect() {
        Specification spec = new Ex4UserSpecification(new Ex4SearchCriteria("age", SearchOperation.GREATER_THAN, "25"));

        List<User> results = repository.findAll(spec);

        assertFalse(results.contains(john));
        assertTrue(results.contains(tom));
    }

    @Test
    public void givenFirstNamePrefix_whenGettingListOfUsers_thenCorrect() {
        Specification spec = new Ex4UserSpecification(new Ex4SearchCriteria("firstName", SearchOperation.STARTS_WITH, "jo"));

        List<User> results = repository.findAll(spec);

        assertTrue(results.contains(john));
        assertFalse(results.contains(tom));
    }

    @Test
    public void givenFirstNameSuffix_whenGettingListOfUsers_thenCorrect() {
        Specification spec = new Ex4UserSpecification(new Ex4SearchCriteria("firstName", SearchOperation.ENDS_WITH, "n"));

        List<User> results = repository.findAll(spec);

        assertTrue(results.contains(john));
        assertFalse(results.contains(tom));
    }

    @Test
    public void givenFirstNameSubstring_whenGettingListOfUsers_thenCorrect() {
        Specification spec = new Ex4UserSpecification(new Ex4SearchCriteria("firstName", SearchOperation.CONTAINS, "oh"));

        List<User> results = repository.findAll(spec);

        assertTrue(results.contains(john));
        assertFalse(results.contains(tom));
    }

    @Test
    public void givenAgeRange_whenGettingListOfUsers_thenCorrect() {
        Specification spec1 = new Ex4UserSpecification(new Ex4SearchCriteria("age", SearchOperation.GREATER_THAN, 20));
        Specification spec2 = new Ex4UserSpecification(new Ex4SearchCriteria("age", SearchOperation.LESS_THAN, 25));

        List<User> results = repository.findAll(Specification.where(spec1).and(spec2));

        assertTrue(results.contains(john));
        assertFalse(results.contains(tom));
    }
}
