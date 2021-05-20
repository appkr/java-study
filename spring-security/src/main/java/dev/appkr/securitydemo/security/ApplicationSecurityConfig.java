package dev.appkr.securitydemo.security;

import static dev.appkr.securitydemo.security.ApplicationUserRole.ADMIN;
import static dev.appkr.securitydemo.security.ApplicationUserRole.ADMINTRAINEE;
import static dev.appkr.securitydemo.security.ApplicationUserRole.STUDENT;

import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;

  public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
//        .antMatchers("/api/**").hasRole(STUDENT.name())
//        .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//        .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//        .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//        .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
        .anyRequest()
        .authenticated()
      .and()
        .formLogin()
        .loginPage("/login").permitAll()
        .defaultSuccessUrl("/courses", true)
//        .passwordParameter("password")
//        .usernameParameter("username")
      .and()
        .rememberMe()
        .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
        .key("supersecurekey")
//        .rememberMeParameter("remember-me")
      .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
        .logoutUrl("/logout")
        .clearAuthentication(true)
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID", "remember-me")
        .logoutSuccessUrl("/login")
        ;
    // @formatter:on
  }

  @Override
  @Bean
  protected UserDetailsService userDetailsService() {
    UserDetails annaSmithUser = User.builder()
        .username("anna")
        .password(passwordEncoder.encode("password"))
//        .roles(STUDENT.name())
        .authorities(STUDENT.getGrantedAuthorities())
        .build();

    UserDetails lindaUser = User.builder()
        .username("linda")
        .password(passwordEncoder.encode("password"))
//        .roles(ADMIN.name())
        .authorities(ADMIN.getGrantedAuthorities())
        .build();

    UserDetails tomUser = User.builder()
        .username("tom")
        .password(passwordEncoder.encode("password"))
//        .roles(ADMINTRAINEE.name())
        .authorities(ADMINTRAINEE.getGrantedAuthorities())
        .build();

    return new InMemoryUserDetailsManager(annaSmithUser, lindaUser, tomUser);
  }
}
