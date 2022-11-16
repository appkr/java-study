package dev.appkr.demo.chat.config;

import dev.appkr.demo.chat.domain.ChannelRepository;
import dev.appkr.demo.chat.handler.ChatChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.net.InetSocketAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(NettyProperties.class)
public class NettyConfiguration {

  final NettyProperties properties;

  @Bean
  public ServerBootstrap bootstrap(ChatChannelInitializer initializer) {
    final ServerBootstrap b = new ServerBootstrap();
    b.group(bossGroup(), workerGroup())
        .channel(NioServerSocketChannel.class)
        .handler(new LoggingHandler(LogLevel.DEBUG))
        .childHandler(initializer);
    b.option(ChannelOption.SO_BACKLOG, properties.getBacklog());

    return b;
  }

  @Bean(destroyMethod = "shutdownGracefully")
  public NioEventLoopGroup bossGroup() {
    return new NioEventLoopGroup(properties.getBossCount());
  }

  @Bean(destroyMethod = "shutdownGracefully")
  public NioEventLoopGroup workerGroup() {
    return new NioEventLoopGroup(properties.getWorkerCount());
  }

  @Bean
  public InetSocketAddress tcpSocketAddress() {
    return new InetSocketAddress(properties.getTcpPort());
  }

  @Bean
  public ChannelRepository channelRepository() {
    return new ChannelRepository();
  }
}
