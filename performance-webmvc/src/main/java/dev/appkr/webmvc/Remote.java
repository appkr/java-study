package dev.appkr.webmvc;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController public class Remote {

  @GetMapping("/remote1")
  @SneakyThrows
  public String remote1(String req) {
    Thread.sleep(1000);
    return  req + "/remote1";
  }

  @GetMapping("/remote2")
  @SneakyThrows
  public String remote2(String req) {
    Thread.sleep(1000);
    return  req + "/remote2";
  }

  public static void main(String[] args) {
    System.setProperty("server.port", "8081");
    SpringApplication.run(Remote.class, args);
  }
}