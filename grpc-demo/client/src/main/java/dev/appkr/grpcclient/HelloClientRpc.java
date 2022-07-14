package dev.appkr.grpcclient;

import com.google.protobuf.Empty;
import dev.appkr.grpcdemo.HelloReply;
import dev.appkr.grpcdemo.HelloRequest;
import dev.appkr.grpcdemo.HelloServiceGrpc.HelloServiceBlockingStub;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HelloClientRpc {

  @GrpcClient(value = "hello-service")
  HelloServiceBlockingStub helloStub;

  public HelloReply hello(String name) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("authentication: {}", authentication);

    final HelloRequest request = HelloRequest.newBuilder()
        .setName(name)
        .build();

    return helloStub.sayHello(request);
  }

  public void invalidArgument() {
    try {
      helloStub.invalidArgument(Empty.newBuilder().build());
    } catch (Exception e) {
      log.error("ERROR", e);
    }
  }

  public void notFound() {
    try {
      helloStub.notFound(Empty.newBuilder().build());
    } catch (Exception e) {
      log.error("ERROR", e);
    }
  }
}
