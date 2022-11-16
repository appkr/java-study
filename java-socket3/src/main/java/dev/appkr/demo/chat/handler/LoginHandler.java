package dev.appkr.demo.chat.handler;

import static dev.appkr.demo.chat.config.Constants.LINE_SEPARATOR;

import dev.appkr.demo.chat.domain.ChannelRepository;
import dev.appkr.demo.chat.domain.User;
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
public class LoginHandler extends ChannelInboundHandlerAdapter {

  final ChannelRepository repository;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (!(msg instanceof String) || !(Objects.toString(msg).startsWith("login "))) {
      ctx.fireChannelRead(msg);
      return;
    }

    final String message = Objects.toString(msg);
    final User user = User.of(message, ctx.channel());
    user.login(repository, ctx.channel());

    ctx.writeAndFlush(String.format("Successfully logged in as '%s'", user.getUsername()) + LINE_SEPARATOR);
    log.info("There are {} bound channels", repository.size());
  }
}
