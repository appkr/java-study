package dev.appkr.reactiveweb.filter;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PlayerHandler {

  Mono<ServerResponse> getName(ServerRequest req) {
    final Mono<String> name = Mono.just(req.pathVariable("name"));
    return ok().body(name, String.class);
  }
}
