package dev.appkr.webflux;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
@Slf4j
public class Webflux {

  static ExecutorService es = Executors.newCachedThreadPool();
  static final String URL1 = "http://localhost:8081/remote1?req={req}";
  static final String URL2 = "http://localhost:8081/remote2?req={req}";

  final WebClient webClient = WebClient.create();
  final InternalService internalService;

  @GetMapping("/rest")
  public Mono<String> rest(String idx) {
    return webClient.get().uri(URL1, idx).retrieve().bodyToMono(String.class)
        .flatMap(res1 -> webClient.get().uri(URL2, res1).retrieve().bodyToMono(String.class))
        .doOnNext(log::info)
        .flatMap(res2 -> Mono.fromCompletionStage(internalService.handle(res2)))
        .doOnNext(log::info);
  }

  @Service
  static class InternalService {
    @SneakyThrows
    public CompletableFuture<String> handle(String req) {
//      Thread.sleep(1000);
      return CompletableFuture.supplyAsync(() -> req + "/asyncWork", es);
    }
  }

  public static void main(String[] args) {
    System.setProperty("server.port", "8080");
    System.setProperty("reactor.ipc.netty.workerCount", "1");
    System.setProperty("reactor.ipc.netty.pool.maxConnections", "2000");
    SpringApplication.run(Webflux.class, args);
  }
}
