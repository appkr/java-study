package dev.appkr.springdata.jpaevent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JpaLifeCycleEventUserTest {

  private JpaLifeCycleEventUser sut;

  @Autowired
  private JpaLifeCycleEventUserRepository repository;

  @BeforeEach
  public void setUp() {
    JpaLifeCycleEventUser user = new JpaLifeCycleEventUser();
    user.setFirstName("Jane");
    user.setLastName("Doe");
    user.setUserName("janedoe");
    this.sut = repository.save(user);
  }

  @AfterEach
  public void tearDown() {
    repository.deleteAll();
  }

  @Test
  @Transactional
  public void testUpdate() {
    sut.setFirstName("Modified");
  }
}