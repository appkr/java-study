package dev.appkr.grpcserver;

import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfiguration {

  OAuth2ResourceServerProperties properties;

  public SecurityConfiguration(OAuth2ResourceServerProperties properties) {
    this.properties = properties;
  }

  @Bean
  GrpcAuthenticationReader authenticationReader() {
    return new BearerAuthenticationReader(accessToken -> new BearerTokenAuthenticationToken(accessToken));
  }

  @Bean
  AuthenticationManager authenticationManager() {
    final JwtDecoder jwtDecoder = NimbusJwtDecoder
        .withJwkSetUri(properties.getJwt().getJwkSetUri())
        .jwsAlgorithm(SignatureAlgorithm.RS256)
        .build();
    final AuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtDecoder);

    return new ProviderManager(List.of(jwtAuthenticationProvider));
  }

//  @Bean
//  GrpcSecurityMetadataSource grpcSecurityMetadataSource() {
//    final ManualGrpcSecurityMetadataSource source = new ManualGrpcSecurityMetadataSource();
//    source.set(HelloServiceGrpc.getSayHelloMethod(),
//        AccessPredicate.authenticated().and(AccessPredicate.hasRole("ROLE_USER")));
//    source.setDefault(AccessPredicate.permitAll());
//
//    return source;
//  }
//
//  @Bean
//  AccessDecisionManager accessDecisionManager() {
//    return new AffirmativeBased(List.of(new AccessPredicateVoter()));
//  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeRequests(spec -> spec
            .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers("/actuator/health/**", "/actuator/info", "/actuator/metrics/**").permitAll()
            .anyRequest().authenticated())
        .build();
  }
}
