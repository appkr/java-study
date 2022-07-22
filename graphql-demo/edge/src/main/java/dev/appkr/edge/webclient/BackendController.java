package dev.appkr.edge.webclient;

import dev.appkr.shared.model.Album;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BackendController {

  WebClient webClient;

  public BackendController(WebClient webClient) {
    this.webClient = webClient;
  }

  @QueryMapping
  public Flux<Album> albums() {
    return this.webClient
        .get()
        .uri("http://localhost:8081/api/albums")
        .retrieve()
        .bodyToFlux(Album.class);
  }

  @BatchMapping // To avoid N+1 network or db io
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
