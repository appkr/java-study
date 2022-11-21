package dev.appkr.demo.tcp;

public interface TcpMessage {

  String getName();
  void setName(String name);
  int getPointer();
  String getValue();
  void setValue(String value);
}
