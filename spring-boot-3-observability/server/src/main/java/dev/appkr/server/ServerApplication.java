package dev.appkr.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@GetMapping("/client-to-server")
	String clientToServer() {
		log.info("Got a request from the client");
		return "foo";
	}

	@GetMapping("/legacy-to-server")
	String legacyToServer() {
		log.info("Got a request from the legacy");
		return "foo";
	}
}
