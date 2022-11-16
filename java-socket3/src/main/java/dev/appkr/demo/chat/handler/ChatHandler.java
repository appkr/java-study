package dev.appkr.demo.chat.handler;

import static dev.appkr.demo.chat.config.Constants.LINE_SEPARATOR;

import dev.appkr.demo.chat.domain.ChannelRepository;
import dev.appkr.demo.chat.domain.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Sharable
@Slf4j
public class ChatHandler extends ChannelInboundHandlerAdapter {

  final ChannelRepository repository;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ctx.fireChannelActive();
    final String remoteAddr = ctx.channel().remoteAddress().toString();
    ctx.writeAndFlush(String.format("The remote address is %s", remoteAddr + LINE_SEPARATOR));
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    final String message = Objects.toString(msg);
    if (message.startsWith("login ")) {
      ctx.fireChannelRead(msg);
      return;
    }

    final String[] split = message.split("::");
    if (split.length != 2) {
      ctx.channel().writeAndFlush(message + LINE_SEPARATOR);
      return;
    }

    final Channel thatChannel = repository.get(split[0]);
    User.current(ctx.channel()).tell(thatChannel, split[0], split[1]);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    log.error(cause.getMessage(), cause);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) {
    User.current(ctx.channel()).logout(repository, ctx.channel());
    log.info("There are {} bound channels", repository.size());
  }
}
