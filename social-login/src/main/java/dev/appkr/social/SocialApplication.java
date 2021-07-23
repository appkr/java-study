package dev.appkr.social;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class SocialApplication extends WebSecurityConfigurerAdapter {

  @GetMapping("/user")
  public OAuth2User user(@AuthenticationPrincipal OAuth2User principal) {
    return principal;
  }

  @GetMapping("/error")
  @ResponseBody
  public String error(HttpServletRequest request) {
    String message = (String) request.getSession().getAttribute("error.message");
    request.getSession().removeAttribute("error.message");
    return message;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler("/");

    // @formatter:off
    http
        .csrf(c -> c
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        )
        .authorizeRequests(a -> a
            .antMatchers("/", "/error").permitAll()
            .anyRequest().authenticated()
        )
        .exceptionHandling(e -> e
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        )
        .oauth2Login()
      .and()
        .logout(l -> l
            .logoutSuccessUrl("/").permitAll()
        );
    // @formatter:on
  }

  @Bean
  public OAuth2UserService oAuth2UserService() {
    return new DefaultOAuth2UserService() {
      @Override
      public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        final OAuth2User oAuth2User = super.loadUser(userRequest);

        // TODO @appkr
        //   - create new ApplicationUser if the email DOESNOT exist
        //   - update existing ApplicationUser if the email DOES exist

        return oAuth2User;
      }
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(SocialApplication.class, args);
  }

}
