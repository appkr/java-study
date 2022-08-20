package dev.appkr.grpcclient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.security.oauth2.resourceserver.jwt.jwk-set-uri = http://dummy/jwks.json")
class GrpcClientApplicationTests {

	@Test
	void contextLoads() {
	}

}
