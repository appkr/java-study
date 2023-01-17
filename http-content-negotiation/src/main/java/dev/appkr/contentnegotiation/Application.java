package dev.appkr.contentnegotiation;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostMapping(value = "/test", produces = "application/json")
	public Map<String, String> example(HttpServletRequest req) {
		try {
			MediaType.parseMediaTypes(req.getHeader(HttpHeaders.ACCEPT));
		} catch (Exception e) {
			return Map.of("error", "Inavlid Accept header value");
		}

		return Map.of("key", "value");
	}
}
