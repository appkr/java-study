package dev.appkr.grpcserver;

import com.google.protobuf.Empty;
import dev.appkr.grpcdemo.HelloReply;
import dev.appkr.grpcdemo.HelloRequest;
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

  @Override
  public void invalidArgument(Empty request, StreamObserver<Empty> responseObserver) {
    responseObserver.onError(new IllegalArgumentException());
  }

  @Override
  public void notFound(Empty request, StreamObserver<Empty> responseObserver) {
    responseObserver.onError(new ResourceNotFoundException());
  }
}
