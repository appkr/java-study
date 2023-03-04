package dev.appkr.example;

import dev.appkr.example.config.ApplicationProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(ApplicationProperties.class)
class ApplicationTest {

	@Test
	void contextLoads() {
	}

}
