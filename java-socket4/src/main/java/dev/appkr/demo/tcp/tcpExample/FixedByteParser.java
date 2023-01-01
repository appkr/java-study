package dev.appkr.demo.tcp.tcpExample;

import dev.appkr.demo.tcp.Item;
import dev.appkr.demo.tcp.Packet;
import dev.appkr.demo.tcp.TcpMessage;
import dev.appkr.demo.tcp.TcpMessageTemplateFactory;
import dev.appkr.demo.tcp.visitor.Parser;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Setter;

public class FixedByteParser implements Parser {

  @Setter
  private Charset charset = StandardCharsets.UTF_8;

  private final TcpMessageTemplateFactory templateFactory;

  public FixedByteParser(TcpMessageTemplateFactory templateFactory) {
    this.templateFactory = templateFactory;
  }

  @Override
  public void parse(Packet parseable) {
    final byte[] src = parseable.getTcpMessage();
    final List<TcpMessage> components = templateFactory.create(src);
    parseable.setMessageComponents(components);

    doParse(src, components, new AtomicInteger(0));
  }

  private void doParse(byte[] src, List<TcpMessage> components, AtomicInteger subPacketIndex) {
    final AtomicInteger srcPos = new AtomicInteger(0);
    components.stream()
        .forEach(component -> {
          final int length = getLength(component);
          final byte[] fragment = new byte[length];
          System.arraycopy(src, srcPos.getAndAdd(length), fragment, 0, length);

          if (component instanceof Item) {
            final String value = new String(fragment, charset).trim();
            component.setValue(value);
          } else {
            final String subPacketName = String.valueOf(subPacketIndex.getAndIncrement());
            component.setName(subPacketName);
            ((Packet) component).setTcpMessage(fragment);
            doParse(fragment, ((Packet) component).getMessageComponents(), subPacketIndex);
          }
        });
  }

  private int getLength(TcpMessage component) {
    if (component instanceof Item) {
      return component.getPointer();
    }

    return ((Packet) component).getMessageComponents().stream()
        .map(this::getLength)
        .reduce(Integer::sum)
        .orElse(0);
  }
}
