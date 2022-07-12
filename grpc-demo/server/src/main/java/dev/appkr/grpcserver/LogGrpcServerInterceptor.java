// @see https://stackoverflow.com/a/68102211/4737224
package dev.appkr.grpcserver;

import io.grpc.ForwardingServerCall.SimpleForwardingServerCall;
import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogGrpcServerInterceptor implements ServerInterceptor {

  @Override
  public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
      ServerCallHandler<ReqT, RespT> next) {
    ServerCall<ReqT, RespT> listener = new SimpleForwardingServerCall<>(call) {
      @Override
      public void sendMessage(RespT message) {
        log.info("gRPC response at server\n{}",  message);
        super.sendMessage(message);
      }
    };

    return new SimpleForwardingServerCallListener<ReqT>(next.startCall(listener, headers)) {
      @Override
      public void onMessage(ReqT message) {
        log.info("gRPC request at server\n{}", message);
        super.onMessage(message);
      }
    };
  }
}
