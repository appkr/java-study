package dev.appkr.demo.tcp.tcpExample;

import dev.appkr.demo.tcp.Item;
import dev.appkr.demo.tcp.Packet;
import dev.appkr.demo.tcp.TcpMessage;
import dev.appkr.demo.tcp.visitor.Formatter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.Setter;

public class FixedByteFormatter implements Formatter {

  @Setter
  private Charset charset = StandardCharsets.UTF_8;

  @Setter
  private String leftPadding = "0";


  @Override
  public void format(Packet formattable) {
    final String formatted = doFormat(formattable.getMessageComponents());
    formattable.setTcpMessage(formatted.getBytes(charset));
  }

  private String doFormat(List<TcpMessage> components) {
    final StringBuilder formatted = new StringBuilder();
    components.stream()
        .forEach(component -> {
          String fragment = "";
          if (component instanceof Item) {
            fragment = formatFor(component);
          } else {
            fragment = doFormat(((Packet)component).getMessageComponents());
          }

          formatted.append(fragment);
        });

    return formatted.toString();
  }

  private String formatFor(TcpMessage item) {
    final int expectedLength = item.getPointer();
    final String value = item.getValue();
    final int lpadCount = expectedLength - value.getBytes(charset).length;
    final StringBuilder sb = new StringBuilder();
    while (sb.length() < lpadCount) {
      sb.append(leftPadding);
    }

    return sb.append(value).toString();
  }
}
