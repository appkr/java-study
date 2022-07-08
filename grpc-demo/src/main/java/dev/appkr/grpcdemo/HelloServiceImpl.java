package dev.appkr.grpcdemo;

import dev.appkr.grpcdemo.HelloServiceGrpc.HelloServiceImplBase;
import io.grpc.stub.StreamObserver;
import java.util.Random;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloServiceImpl extends HelloServiceImplBase {

  @Override
  public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
    final HelloReply reply = HelloReply.newBuilder()
        .setMessage("Hello " + request.getName())
        .setLuckyNumber(new Random().nextInt())
        .build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }
}
