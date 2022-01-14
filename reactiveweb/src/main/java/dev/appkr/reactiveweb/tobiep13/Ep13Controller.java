package dev.appkr.reactiveweb.tobiep13;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class Ep13Controller {

  @GetMapping("/hello")
  Mono<String> hello() {
    log.info("pos1");
    final Mono<String> m = Mono.fromSupplier(() -> generateHello())
        .doOnNext(c -> log.info(c))
        .log();
    log.info("pos2");
    return m;
  }

  String generateHello() {
    log.info("method generateHello()");
    return "Hello Mono";
  }
}
