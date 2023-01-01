package dev.appkr.demo.tcp.config;

import java.nio.charset.Charset;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tcp.client")
public class TcpClientProperties {

  final String host = "localhost";
  final int port = 65535;
  final int connectionTimeout = 1_000; // millis
  final int readTimeout = 5_000; // millis
  @Getter(AccessLevel.NONE)
  final String charset = "utf-8";
  final Pool pool = new Pool();

  public Charset getCharset() {
    return Charset.forName(charset);
  }

  @Getter
  @Setter
  public static class Pool {
    final int minIdle = 10;
    final int maxIdle = 10;
    final int maxTotal = 100;
  }
}
