package dev.appkr.demo.tcp;

import java.util.List;

public interface TcpMessageTemplateFactory {

  // tcpMessage 길이를 분석하여, 하위 Packet의 반복 횟수를 구하여 객체를 생성한다
  List<TcpMessage> create(byte[] tcpMessage);
}
