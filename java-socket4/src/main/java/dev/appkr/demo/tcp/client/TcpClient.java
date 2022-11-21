package dev.appkr.demo.tcp.client;

public interface TcpClient {

  void write(byte[] message) throws Exception;
  byte[] read() throws Exception;
}
