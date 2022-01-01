package dev.appkr.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
  RouteLocator routes(RouteLocatorBuilder builder) {
	  return builder.routes()
        .route("backend", r -> r.path("/backend/**")
            .filters(f -> f.rewritePath("/backend/(?<remaining>.*)", "/${remaining}")
//                .filter((exchange, chain) -> {
//                  exchange.getRequest().mutate().header("X-Foo", "bar");
//                  return chain.filter(exchange);
//                })
            )
            .uri("http://localhost:8081"))
        .build();
  }
}

@Component
class CustomHeaderGlobalFilter implements GlobalFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    exchange.getRequest().mutate().header("X-Foo", "bar");
    return chain.filter(exchange);
  }
}
