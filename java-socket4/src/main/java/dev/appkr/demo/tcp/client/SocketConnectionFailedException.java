package dev.appkr.demo.tcp.client;

public class SocketConnectionFailedException extends RuntimeException {

  public SocketConnectionFailedException(String message) {
    super(message);
  }

  public SocketConnectionFailedException(String message, Throwable cause) {
    super(message, cause);
  }
}
