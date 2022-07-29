package dev.appkr.edge.webclient;

import dev.appkr.shared.model.Album;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class BackendController {

  WebClient webClient;

  public BackendController(WebClient webClient) {
    this.webClient = webClient;
  }

  @QueryMapping
  public Flux<Album> albums(@Argument Integer page, @Argument Integer size) {
    return ReactiveSecurityContextHolder.getContext()
        .map(securityContext -> securityContext.getAuthentication().getPrincipal())
        .cast(Jwt.class)
        .doOnNext(jwt -> log.info("username: {}", jwt.getClaim("user_name").toString()))
        .thenMany(this.webClient
            .get()
            .uri("http://localhost:8081/api/albums?page={page}&size={size}", page, size)
            .retrieve()
            .bodyToFlux(Album.class));
  }

  @BatchMapping
    // To avoid N+1 network or db io
  Map<Album, Integer> price(List<Album> albums) {
    // Simulate network OR database call.
    // NOTE. To be used as a unique key, class Album must implement hashcode and equals.
    final Map<Album, Integer> map = new HashMap<>();
    for (Album album : albums) {
      map.put(album, 10_000);
    }

    return map;
  }
}
