package dev.appkr.grpcserver;

import static org.junit.jupiter.api.Assertions.*;

import dev.appkr.grpcdemo.HelloReply;
import dev.appkr.grpcdemo.HelloRequest;
import dev.appkr.grpcserver.HelloServiceImpl;
import io.grpc.internal.testing.StreamRecorder;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.security.oauth2.resourceserver.jwt.jwk-set-uri = http://dummy/jwks.json")
class HelloServiceTest {

  @Autowired
  HelloServiceImpl sut;

  @Test
  void sayHello() throws Exception {
    final HelloRequest req = HelloRequest.newBuilder()
        .setName("test")
        .build();

    // when
    final StreamRecorder<HelloReply> responseObserver = StreamRecorder.create();
    sut.sayHello(req, responseObserver);
    if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
      fail("The call did not terminate in the given time");
    }

    // then
    assertNull(responseObserver.getError());

    final List<HelloReply> values = responseObserver.getValues();
    assertEquals(1, values.size());

    final HelloReply res = values.get(0);
    assertEquals("Hello test", res.getMessage());
  }
}