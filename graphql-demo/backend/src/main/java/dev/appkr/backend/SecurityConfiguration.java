package dev.appkr.backend;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.oauth2.jwt.NimbusJwtDecoder.withJwkSetUri;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

  OAuth2ResourceServerProperties properties;

  public SecurityConfiguration(OAuth2ResourceServerProperties properties) {
    this.properties = properties;
  }

  // NOTE In Spring Security 5.7.0-M2 we deprecated the WebSecurityConfigurerAdapter
  //      as we encourage users to move towards a component-based security configuration.
  // @see https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
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
            // NOTE. graphql UI를 이용할 때 오류가 나지 않도록 임시로 허용
            .antMatchers("/api/**").permitAll()
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
