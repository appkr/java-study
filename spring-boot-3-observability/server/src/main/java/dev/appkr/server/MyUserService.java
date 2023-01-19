package dev.appkr.server;

import io.micrometer.observation.annotation.Observed;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyUserService {

  final Random random = new Random();

  @Observed(
      name = "user.name", // <user.name> will be used as a metric name
      contextualName = "getting-user-name", // <getting-user-name> will be used as a span name
      lowCardinalityKeyValues = {"userType", "userType2"} // <userType=userType2> will be set as a tag for both metric & span
  )
  public String username(String userId) {
    log.info("Getting username for user with id <{}>", userId);
    try {
      Thread.sleep(random.nextLong(200L)); // Simulates latency
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return "foo";
  }
}
