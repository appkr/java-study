package dev.appkr.grpcserver;

import java.util.List;
import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;

@Configuration(proxyBeanMethods = false)
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfiguration extends ResourceServerConfigurerAdapter {

  ResourceServerProperties properties;

  public SecurityConfiguration(ResourceServerProperties properties) {
    this.properties = properties;
  }

  @Bean
  GrpcAuthenticationReader authenticationReader() {
    return new BearerAuthenticationReader(accessToken -> new BearerTokenAuthenticationToken(accessToken));
  }

  @Bean
  AuthenticationManager authenticationManager() {
    final JwtDecoder jwtDecoder = NimbusJwtDecoder
        .withJwkSetUri(properties.getJwk().getKeySetUri())
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

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        .authorizeRequests()
        .requestMatchers(EndpointRequest.to(HealthEndpoint.class, InfoEndpoint.class, MetricsEndpoint.class)).permitAll()
    ;
    // @formatter:on
  }
}
