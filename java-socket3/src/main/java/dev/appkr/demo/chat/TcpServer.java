package dev.appkr.demo.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import java.net.InetSocketAddress;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TcpServer {

  final ServerBootstrap serverBootstrap;
  final InetSocketAddress socketAddr;

  Channel serverChannel;

  public void start() {
    try {
      final ChannelFuture channelFuture = serverBootstrap.bind(socketAddr).sync();
      log.info("A Tcp server is running at port {}", socketAddr.getPort());
      serverChannel = channelFuture.channel().closeFuture().sync().channel();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @PreDestroy
  public void stop() {
    if (serverChannel != null) {
      serverChannel.closeFuture();
      serverChannel.parent().close();
    }
  }
}
