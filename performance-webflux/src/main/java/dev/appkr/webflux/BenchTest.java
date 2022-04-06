package dev.appkr.webflux;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class BenchTest {

  final WebClient webClient = WebClient.create();

  @GetMapping("/bench")
  @SneakyThrows
  public Mono<String> bench() {
    return Flux.range(0, 30)
        .flatMap(idx -> {
          return webClient.
              get()
              .uri("http://localhost:8081/dbcall")
              .retrieve()
              .bodyToMono(String.class);
        })
        .then(Mono.just("done"));
  }

  public static void main(String[] args) {
    System.setProperty("server.port", "8082");
    // @see reactor.netty.ReactorNetty
    System.setProperty("reactor.netty.ioWorkerCount", "1");
    System.setProperty("reactor.netty.pool.maxConnections", "2000");
    SpringApplication.run(BenchTest.class, args);
  }
}
