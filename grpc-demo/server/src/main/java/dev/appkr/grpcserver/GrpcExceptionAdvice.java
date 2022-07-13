package dev.appkr.grpcserver;

import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.Status;
import io.grpc.StatusException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.springframework.security.access.AccessDeniedException;

@GrpcAdvice
public class GrpcExceptionAdvice {

  @GrpcExceptionHandler
  public Status handleInvalidArgument(IllegalArgumentException e) {
    return Status.INVALID_ARGUMENT.withDescription("Invalid argument").withCause(e);
  }

  @GrpcExceptionHandler
  public Status handleInvalidArgument(AccessDeniedException e) {
    return Status.PERMISSION_DENIED.withDescription("Permission denied").withCause(e);
  }

  @GrpcExceptionHandler(ResourceNotFoundException.class)
  public StatusException handleResourceNotFoundException(ResourceNotFoundException e) {
    final Status status = Status.NOT_FOUND.withDescription("Not found").withCause(e);
    final Metadata metadata = new Metadata();
    metadata.put(Key.of("key", Metadata.ASCII_STRING_MARSHALLER), "value");

    return status.asException(metadata);
  }
}
