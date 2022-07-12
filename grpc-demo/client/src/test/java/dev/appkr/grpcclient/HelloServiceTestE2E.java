package dev.appkr.grpcclient;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.appkr.grpcdemo.HelloReply;
import dev.appkr.grpcdemo.HelloRequest;
import dev.appkr.grpcdemo.HelloServiceGrpc.HelloServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@Disabled("inProcess 채널에 연결된 서버가 떠 있을 때만 테스트 가능함"
    + "즉, grpc-server-spring-boot-starter 의존을 포함하고 서버 코드를 구현해야 테스트 가능함")
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