package springstudy.datacommons;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.awt.geom.Point2D;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatacommonsApplicationTests {

    @Autowired
    private UserRepository userRepository;

    private User defaultUser;

    @Test
    public void contextLoads() {
    }

    @Test
    @Transactional
    @Rollback(false)
    public void canCreateUser() {
        assertNotNull(this.defaultUser.getId());
    }

    @Test
    public void canReadUser() {
        Optional<User> optional = userRepository.findById(this.defaultUser.getId());
        User sut = optional.orElseThrow(() -> new RuntimeException("Not Found"));
        prettyPrint(sut);
        assertEquals(this.defaultUser, sut);
    }

    @Test
    public void canCalculateDistanceBetweenUser() {
        User sut = new User("Two", new Point(127.0522567, 37.5050852));
        double distance = Point2D.distance(
            defaultUser.getLocation().getX(),
            defaultUser.getLocation().getY(),
            sut.getLocation().getX(),
            sut.getLocation().getY()
        );
        prettyPrint(String.format("Distance between %s and %s: %f", defaultUser.getLocation(), sut.getLocation(), distance));
    }

    @Before
    public void createUser() {
        User user = new User();
        user.setName("One");
        user.setLocation(new Point(126.97565350953265, 37.564363903078544));
        this.defaultUser = userRepository.save(user);
    }

    private void prettyPrint(Object o) {
        System.out.println("--------------------------------------------");
        System.out.println(o);
        System.out.println("--------------------------------------------");
    }
}
