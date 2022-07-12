package dev.appkr.grpcdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.appkr.grpcdemo.HelloServiceGrpc.HelloServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
class HelloServiceTestE2E {

  @GrpcClient("inProcess")
  HelloServiceBlockingStub sut;

  @Test
  void sayHello() throws Exception {
    final HelloRequest req = HelloRequest.newBuilder()
        .setName("test")
        .build();

    // when
    final HelloReply res = sut.sayHello(req);

    // then
    assertEquals("Hello test", res.getMessage());
  }
}