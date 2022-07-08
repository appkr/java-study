// @see https://stackoverflow.com/a/68102211/4737224
package dev.appkr.grpcdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.GeneratedMessageV3;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogGrpcClientInterceptor implements ClientInterceptor {

  static final ObjectMapper mapper = new ObjectMapper();

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
      CallOptions callOptions, Channel next) {
    return new SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
      @SneakyThrows
      @Override
      public void sendMessage(ReqT message) {
        final String logEntry = mapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(((GeneratedMessageV3) message).getAllFields());
        log.info("gRPC request at client\n{}", logEntry);
        super.sendMessage(message);
      }

      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        super.start(new SimpleForwardingClientCallListener<RespT>(responseListener) {
          @SneakyThrows
          @Override
          public void onMessage(RespT message) {
            final String logEntry = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(((GeneratedMessageV3) message).getAllFields());
            log.info("gRPC response at client\n{}", logEntry);
            super.onMessage(message);
          }
        }, headers);
      }
    };
  }
}
