package dev.appkr.webmvc;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.stream.IntStream;

@SpringBootApplication
@RestController
@Slf4j
public class BenchTest {

  RestTemplate restTemplate = new RestTemplate();

  @GetMapping("/bench")
  @SneakyThrows
  public String bench() {
    IntStream.range(0, 30)
        .forEach(idx -> {
          // Simulate blocking database call
          restTemplate.getForObject("http://localhost:8081/dbcall", String.class);
        });

    return "done";
  }

  public static void main(String[] args) {
    System.setProperty("server.port", "8082");
    System.setProperty("server.tomcat.threads.max", "10");
    SpringApplication.run(BenchTest.class, args);
  }
}
