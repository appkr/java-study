package springstudy.restquery.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springstudy.restquery.domain.IUserDao;
import springstudy.restquery.domain.User;
import springstudy.restquery.service.dto.SearchCriteria;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JpaCriteriaQueryTest {

    @Autowired
    private IUserDao userDao;
    private User john;
    private User tom;

    @Before
    public void setUp() {
        john = new User();
        john.setFirstName("John");
        john.setLastName("Doe");
        john.setEmail("john@example.com");
        john.setAge(22);
        userDao.save(john);

        tom = new User();
        tom.setFirstName("Tom");
        tom.setLastName("Doe");
        tom.setEmail("tom@example.com");
        tom.setAge(26);
        userDao.save(tom);
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        ArrayList<SearchCriteria> params = new ArrayList<>();
        params.add(new SearchCriteria("firstName", ":", "John"));
        params.add(new SearchCriteria("lastName", ":", "Doe"));

        List<User> results = userDao.searchUser(params);

        assertTrue(results.contains(john));
        assertFalse(results.contains(tom));
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        ArrayList<SearchCriteria> params = new ArrayList<>();
        params.add(new SearchCriteria("lastName", ":", "Doe"));

        List<User> results = userDao.searchUser(params);

        assertTrue(results.contains(john));
        assertTrue(results.contains(tom));
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
        ArrayList<SearchCriteria> params = new ArrayList<>();
        params.add(new SearchCriteria("lastName", ":", "Doe"));
        params.add(new SearchCriteria("age", ">", 25));

        List<User> results = userDao.searchUser(params);

        assertFalse(results.contains(john));
        assertTrue(results.contains(tom));
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        ArrayList<SearchCriteria> params = new ArrayList<>();
        params.add(new SearchCriteria("firstName", ":", "Adam"));
        params.add(new SearchCriteria("lastName", ":", "Fox"));

        List<User> results = userDao.searchUser(params);

        assertTrue(results.isEmpty());
    }

    @Test
    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
        ArrayList<SearchCriteria> params = new ArrayList<>();
        params.add(new SearchCriteria("firstName", ":", "Jo"));

        List<User> results = userDao.searchUser(params);

        assertTrue(results.contains(john));
        assertFalse(results.contains(tom));
    }
}
