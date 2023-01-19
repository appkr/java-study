package dev.appkr.client;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
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

	@Bean
	ApplicationRunner applicationRunner(RestTemplate restTemplate) {
		return arg -> {
			String res1 = restTemplate.getForObject("http://localhost:8080/client-to-server", String.class);
			log.info("Got a response <{}> from the server", res1);

			String res2 = restTemplate.getForObject("http://localhost:8082/client-to-legacy", String.class);
			log.info("Got a response <{}> from the legacy", res2);
		};
	}
}
