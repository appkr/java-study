package dev.appkr.webmvc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
@EnableAsync
public class Webmvc {

  static final String URL1 = "http://localhost:8081/remote1?req={req}";
  static final String URL2 = "http://localhost:8081/remote2?req={req}";

  final RestTemplate restTemplate = new RestTemplate();
  final InternalService internalService;

  @GetMapping("/rest")
  @SneakyThrows
  public String rest(String idx) {
    final String res1 = restTemplate.getForObject(URL1, String.class, idx);
    final String res2 = restTemplate.getForObject(URL2, String.class, res1);
    final Future<String> res3 = internalService.handle(res2);

    return res3.get();
  }

  @Service
  static class InternalService {
    @Async
    public Future<String> handle(String req) {
      return new AsyncResult<>(req + "/internalService");
    }
  }

  public static void main(String[] args) {
    System.setProperty("server.port", "8080");
    System.setProperty("server.tomcat.threads.max", "1");
    SpringApplication.run(Webmvc.class, args);
  }
}
