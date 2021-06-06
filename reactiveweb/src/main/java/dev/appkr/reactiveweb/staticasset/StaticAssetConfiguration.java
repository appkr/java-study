package dev.appkr.reactiveweb.staticasset;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.resources;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class StaticAssetConfiguration {

  @Bean
  public RouterFunction<ServerResponse> htmlRouter(@Value("classpath:/public/index.html")Resource html) {
    return route(
        GET("/"),
        request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(html)
      );
  }

  @Bean
  public RouterFunction<ServerResponse> imgRouter() {
    return resources("/public/images/**", new ClassPathResource("public/images/"));
  }
}
