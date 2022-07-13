package dev.appkr.grpcserver;

import com.google.protobuf.Empty;
import dev.appkr.grpcdemo.HelloReply;
import dev.appkr.grpcdemo.HelloRequest;
import dev.appkr.grpcdemo.HelloServiceGrpc.HelloServiceImplBase;
import io.grpc.stub.StreamObserver;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@GrpcService
@Slf4j
public class HelloServiceImpl extends HelloServiceImplBase {

  @Override
//  @PreAuthorize("hasAuthority('SCOPE_openid')")
  public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("authentication: {}", authentication);
    // authentication: JwtAuthenticationToken [
    //   Principal=org.springframework.security.oauth2.jwt.Jwt@294233d8,
    //   Credentials=[PROTECTED],
    //   Authenticated=true,
    //   Details={io.grpc.internal.GrpcAttributes.securityLevel=NONE, local-addr=/0:0:0:0:0:0:0:1:8090, remote-addr=/0:0:0:0:0:0:0:1:56059}, Granted Authorities=[SCOPE_openid]
    // ]

    final HelloReply reply = HelloReply.newBuilder()
        .setMessage("Hello " + request.getName())
        .setLuckyNumber(new Random().nextInt())
        .setPrincipal(authentication != null ? authentication.getPrincipal().toString() : "unknown")
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
