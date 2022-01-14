package dev.appkr.reactiveweb.manual;

import java.security.Principal;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Slf4j
public class TestController {

  @GetMapping("/controller-injection")
  public void controllerInjection(
      ServerWebExchange exchange,
      Principal principal,
      HttpMethod method,
      Locale locale,
      TimeZone timezone,
      ZoneId zoneId,
      UriComponentsBuilder uriComponentsBuilder
  ) {
    log.info("{} {} {}", exchange.getRequest(), exchange.getResponse(), exchange.getSession());
    log.info("{}", principal);
    log.info("{}", method);
    log.info("{}", locale);
    log.info("{}", timezone);
    log.info("{}", zoneId);
    log.info("{}", uriComponentsBuilder.path("test").build().toUriString());
  }

  @GetMapping("/header-injection")
  public void headerInjection(
      @RequestHeader("Host") String host,
      @RequestHeader("Accept") String[] accepts
  ) {
    log.info("{}", host);
    log.info("{}", Arrays.toString(accepts));
  }

  @GetMapping("/cookie-injection")
  public void cookieInjection(
      @CookieValue("JSESSIONID") String cookie
  ) {
    log.info("{}", cookie);
  }
}
