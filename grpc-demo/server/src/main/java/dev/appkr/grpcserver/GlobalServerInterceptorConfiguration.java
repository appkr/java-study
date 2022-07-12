package dev.appkr.grpcserver;

import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalServerInterceptorConfiguration {

  @GrpcGlobalServerInterceptor
  LogGrpcServerInterceptor logGrpcServerInterceptor() {
    return new LogGrpcServerInterceptor();
  }
}
