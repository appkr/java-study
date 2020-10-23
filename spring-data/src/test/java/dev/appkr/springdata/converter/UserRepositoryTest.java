package dev.appkr.springdata.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.awt.geom.Point2D;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserRepository userRepository;

  private User defaultUser;

  @Test
  public void canCreateUser() {
    assertNotNull(this.defaultUser.getId());
  }

  @Test
  public void canReadUser() {
    Optional<User> optional = userRepository.findById(this.defaultUser.getId());
    User sut = optional.orElseThrow(() -> new RuntimeException("Not Found"));
    log.info("canReadUser -  {}", sut);
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
    log.info("canCalculateDistanceBetweenUser - Distance between {} and {}: {}",
        defaultUser.getLocation(), sut.getLocation(), distance);
  }

  @BeforeEach
  @Transactional
  public void createUser() {
    User user = new User();
    user.setName("One");
    user.setLocation(new Point(126.97565350953265, 37.564363903078544));
    this.defaultUser = userRepository.save(user);
  }
}
