package dev.appkr.grpcdemo;

import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration(proxyBeanMethods = false)
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalClientInterceptorConfiguration {

  @GrpcGlobalClientInterceptor
  LogGrpcClientInterceptor logGrpcClientInterceptor() {
    return new LogGrpcClientInterceptor();
  }
}
