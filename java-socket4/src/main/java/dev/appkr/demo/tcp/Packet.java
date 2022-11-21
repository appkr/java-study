package dev.appkr.demo.tcp;

import static java.util.function.Function.identity;

import dev.appkr.demo.tcp.visitor.Formattable;
import dev.appkr.demo.tcp.visitor.Formatter;
import dev.appkr.demo.tcp.visitor.Parceable;
import dev.appkr.demo.tcp.visitor.Parser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString(of = {"messageComponents"})
public class Packet implements Parceable, Formattable, TcpMessage {

  private List<TcpMessage> messageComponents = new ArrayList<>();

  private String name;
  private int pointer = -1; // 편의를 위해 ISP(인터페이스 분리 원칙) 위반
  private String value;     // 편의를 위해 ISP(인터페이스 분리 원칙) 위반
  private byte[] tcpMessage;

  public Packet(String name) {
    this.name = name;
  }

  public Packet(String name, byte[] tcpMessage) {
    this.name = name;
    this.tcpMessage = tcpMessage;
  }

  @Override
  public void accept(Parser parser) {
    // 받는 메시지이므로, 받기 전에 반복부의 정확한 갯수를 몰라서, Packet 객체를 미리 생성할 수 없다고 가정함
    parser.parse(this);
  }

  @Override
  public void accept(Formatter formatter) {
    // 보내는 메시지이므로, 메시지의 정확한 스펙을 이미 알고 있고, Packget 객체를 미리 생성할 수 있다고 가정함
    formatter.format(this);
  }

  public void add(TcpMessage component) {
    this.messageComponents.add(component);
  }

  public Map<String, TcpMessage> toMap() {
    return this.messageComponents.stream()
        .collect(Collectors.toMap(TcpMessage::getName, identity()));
  }
}
