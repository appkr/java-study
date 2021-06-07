package dev.appkr.reactiveweb.annotation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {
    final EmployeeWebClient client = new EmployeeWebClient();
    client.consume();
  }
}
