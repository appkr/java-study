package dev.appkr.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Ignored because the EchoServer is blocking indefinitely")
class ApplicationTests {

	@Test
	void contextLoads() {
	}
}
