package dev.appkr.demo.chat.config;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "netty")
public class NettyProperties {

  @NotNull
  @Size(min = 1_000, max = 65_535)
  int tcpPort;

  @NotNull
  @Min(1)
  int bossCount;

  @NotNull
  @Min(2)
  int workerCount;

  @NotNull
  boolean keepAlive;

  @NotNull
  int backlog;
}
