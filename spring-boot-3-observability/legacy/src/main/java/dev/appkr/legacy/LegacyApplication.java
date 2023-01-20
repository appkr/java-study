package dev.appkr.legacy;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@Slf4j
public class LegacyApplication {

  public static void main(String[] args) {
    SpringApplication.run(LegacyApplication.class, args);
  }

  @Bean
  RestTemplate restTemplate(RestTemplateBuilder builder) {
    final ClientHttpRequestInterceptor loggingInterceptor = (request, body, execution) -> {
      final Map<String, Object> reqLog = Map.of("headers", request.getHeaders());
      log.info("REQUEST: {}", reqLog);

      final ClientHttpResponse response = execution.execute(request, body);

      final Map<String, Object> resLog = Map.of("headers", response.getHeaders());
      log.info("RESPONSE: {}", resLog);

      return response;
    };

    return builder
        .interceptors(loggingInterceptor)
        .build();
  }

  @GetMapping(path = "/client-to-legacy")
  String clientToLegacy() {
    log.info("Got a request");
    return "foo";
  }

  @Bean
  ApplicationRunner applicationRunner(RestTemplate restTemplate) {
    return arg -> {
      String res1 = restTemplate.getForObject("http://localhost:8080/legacy-to-server", String.class);
      log.info("Get a response <{}> from the server", res1);
    };
  }
}
