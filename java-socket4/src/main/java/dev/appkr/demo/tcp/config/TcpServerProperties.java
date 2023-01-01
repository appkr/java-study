package dev.appkr.demo.tcp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tcp.server")
public class TcpServerProperties {

  int port = 65_535;
  int maxConnection = 100;
}
