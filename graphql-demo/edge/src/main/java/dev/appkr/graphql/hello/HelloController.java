package dev.appkr.graphql.hello;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class HelloController {

  @QueryMapping
  public Mono<String> hello() {
    return Mono.just("Hello GraphQL");
  }
}
