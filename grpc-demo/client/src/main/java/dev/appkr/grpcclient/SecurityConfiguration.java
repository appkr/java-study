package dev.appkr.grpcclient;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

import static net.devh.boot.grpc.common.security.SecurityConstants.AUTHORIZATION_HEADER;
import static net.devh.boot.grpc.common.security.SecurityConstants.BEARER_AUTH_PREFIX;
import static org.springframework.security.oauth2.jwt.NimbusJwtDecoder.withJwkSetUri;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

  OAuth2ResourceServerProperties properties;

  public SecurityConfiguration(OAuth2ResourceServerProperties properties) {
    this.properties = properties;
  }

  @Bean
  CallCredentials bearerAuthForwardingCredentials() {
// NOTE. the following code block is not working...
// Which is a bug of 2.13.1 @see https://github.com/yidongnan/grpc-spring-boot-starter/pull/642

//    return CallCredentialsHelper.bearerAuth(() -> {
//      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//      if (authentication != null && authentication instanceof OAuth2Authentication) {
//        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
//        return details.getTokenValue();
//      }
//
//      return null;
//    });

    return new CallCredentials() {

      private final Supplier<Metadata> extraHeadersSupplier = () -> {
        final Metadata extraHeaders = new Metadata();

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof JwtAuthenticationToken) {
          final Jwt jwt = (Jwt) authentication.getPrincipal();
          extraHeaders.put(AUTHORIZATION_HEADER, BEARER_AUTH_PREFIX + jwt.getTokenValue());
        }

        return extraHeaders;
      };

      @Override
      public void applyRequestMetadata(final RequestInfo requestInfo, final Executor appExecutor, final MetadataApplier applier) {
        applier.apply(this.extraHeadersSupplier.get());
      }

      @Override
      public void thisUsesUnstableApi() {} // API evolution in progress

      @Override
      public String toString() {
        return "CallCredentials [extraHeadersSupplier=" + this.extraHeadersSupplier + "]";
      }
    };
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .cors()
        .and()
        .csrf(spec -> spec.disable())
        .headers(spec -> spec.frameOptions().disable())
        .authorizeRequests(spec -> spec
            .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers("/actuator/health/**", "/actuator/info", "/actuator/metrics/**").permitAll()
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer(customizer -> customizer.jwt())
        .build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return withJwkSetUri(properties.getJwt().getJwkSetUri()).build();
  }
}
