package dev.appkr.reactiveweb.errorhandling;

import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HelloHandler {

  public Mono<ServerResponse> handle(ServerRequest request) {
//    return sayHello(request)
//        .onErrorReturn("Hello, stranger")
//        .flatMap(s -> ok().contentType(TEXT_PLAIN).bodyValue(s));

//    return ok().body(sayHello(request).onErrorReturn("Hello, stranger"), String.class);

    return ok().body(sayHello(request), String.class);
  }

  private Mono<String> sayHello(ServerRequest req) {
    try {
      return Mono.just("Hello, " + req.queryParam("name").get());
    } catch (Exception e) {
      return Mono.error(e);
    }
  }
}
