package dev.appkr.reactiveweb.tobiep12;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class Ep12Controller {

  static String BODY;
  static {
    try {
      BODY = new ObjectMapper().writeValueAsString("Foo");
    } catch (JsonProcessingException e) {
    }
  }

  @GetMapping(value = "/rest1", produces = "application/json")
  public ResponseEntity<String> rest1() throws InterruptedException {
    log.info("received rest1@" + Thread.currentThread().getName());
    Thread.sleep(2000L);
    log.info("blocking job done@" + Thread.currentThread().getName());
    return ResponseEntity.ok(BODY);
  }

  @GetMapping(value = "/rest2", produces = "application/json")
  public Mono<ResponseEntity<String>> rest2() throws InterruptedException {
    log.info("received rest2@" + Thread.currentThread().getName());
    Thread.sleep(2000L);
    log.info("blocking job done@" + Thread.currentThread().getName());
    return Mono.just(ResponseEntity.ok(BODY));
  }
}
