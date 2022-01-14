package dev.appkr.reactiveweb.errorhandling;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class HelloRouter {

  @Bean
  public RouterFunction<ServerResponse> helloRoute(HelloHandler handler) {
    return route(GET("/hello").and(accept(MediaType.TEXT_PLAIN)), handler::handle);
  }
}
