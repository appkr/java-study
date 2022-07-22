package dev.appkr.edge;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

  ResourceServerProperties properties;

  public SecurityConfiguration(ResourceServerProperties properties) {
    this.properties = properties;
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http
        .cors()
      .and()
        .csrf(spec -> spec.disable())
        .headers(spec -> spec.frameOptions().disable())
        .authorizeExchange(spec -> spec.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .pathMatchers("/actuator/health/**", "/actuator/info", "/actuator/metrics/**").permitAll()
            // NOTE. graphql UI를 오픈해도, API 엔드포인트가 오픈되어 있지 않으므로 401 오류 남
            .pathMatchers("/graphiql/**", "/graphql/**").permitAll()
            .anyExchange().authenticated())
        .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)
        .build();
  }

  @Bean
  ReactiveJwtDecoder reactiveJwtDecoder() {
    return new NimbusReactiveJwtDecoder(properties.getJwk().getKeySetUri());
  }
}
