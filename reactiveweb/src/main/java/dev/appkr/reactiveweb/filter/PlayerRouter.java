package dev.appkr.reactiveweb.filter;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class PlayerRouter {

  @Bean
  public RouterFunction<ServerResponse> route(PlayerHandler handler) {
    return RouterFunctions
        .route(GET("/player/{name}"), handler::getName)
        .filter(new ExampleHandlerFilterFunction());
  }
}
