package dev.appkr.grpcdemo;

import dev.appkr.grpcdemo.HelloServiceGrpc.HelloServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class HelloClientRpc {

  @GrpcClient(value = "hello-service")
  HelloServiceBlockingStub helloStub;

  public HelloReply hello(String name) {
    final HelloRequest request = HelloRequest.newBuilder()
        .setName(name)
        .build();

    return helloStub.sayHello(request);
  }
}
