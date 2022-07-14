package dev.appkr.grpcclient;

import static net.devh.boot.grpc.common.security.SecurityConstants.AUTHORIZATION_HEADER;
import static net.devh.boot.grpc.common.security.SecurityConstants.BEARER_AUTH_PREFIX;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration  extends ResourceServerConfigurerAdapter {

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
        if (authentication != null && authentication instanceof OAuth2Authentication) {
          final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
          extraHeaders.put(AUTHORIZATION_HEADER, BEARER_AUTH_PREFIX + details.getTokenValue());
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

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        // To avoid an OPTIONS request always making a 401 response:
        // @see https://www.baeldung.com/spring-security-cors-preflight
        .cors()
      .and()
        .csrf().disable()
        .headers().frameOptions().disable()
      .and()
        .sessionManagement().sessionCreationPolicy(STATELESS)
      .and()
        .authorizeRequests()
        .requestMatchers(EndpointRequest.to(HealthEndpoint.class, InfoEndpoint.class, MetricsEndpoint.class)).permitAll()
      .and()
        .exceptionHandling()
    ;
    // @formatter:on
  }
}
