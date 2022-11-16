package dev.appkr.demo.chat.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatChannelInitializer extends ChannelInitializer<SocketChannel> {

  final ChatHandler chatHandler;
  final LoginHandler loginHandler;
  final StringEncoder encoder = new StringEncoder();
  final StringDecoder decoder = new StringDecoder();

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    final ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new DelimiterBasedFrameDecoder(1_024 * 1_024, Delimiters.lineDelimiter()));
    pipeline.addLast(decoder);
    pipeline.addLast(encoder);
    pipeline.addLast(chatHandler);
    pipeline.addLast(loginHandler);
  }
}
