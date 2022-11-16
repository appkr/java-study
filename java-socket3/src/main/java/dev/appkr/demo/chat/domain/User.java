package dev.appkr.demo.chat.domain;

import static dev.appkr.demo.chat.config.Constants.LINE_SEPARATOR;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class User {

  public static final AttributeKey<User> USER_ATTRIBUTE_KEY = AttributeKey.newInstance("USER");

  @Getter
  final String username;
  final Channel channel;

  private User(String username, Channel channel) {
    this.username = username;
    this.channel = channel;
  }

  public static User of (@NotNull String command, @NotNull Channel channel) {
    if (command.startsWith("login ")) {
      return new User(command.trim().substring("login ".length()), channel);
    }

    throw new IllegalArgumentException("Illegal command: " + command);
  }

  public static User current(Channel channel) {
    final User user = channel.attr(USER_ATTRIBUTE_KEY).get();
    if (user == null) {
      throw new UserLoggedOutException();
    }

    return user;
  }

  public void tell(Channel thatChannel, @NotNull String thatUsername, @NotNull String message) {
    if (thatChannel != null) {
      thatChannel.writeAndFlush(this.username);
      thatChannel.write(">");
      thatChannel.writeAndFlush(message + LINE_SEPARATOR);
      this.channel.writeAndFlush(String.format("The message was sent to '%s' successfully", thatUsername) + LINE_SEPARATOR);
    } else {
      this.channel.writeAndFlush(String.format("No user named as '%s'", thatUsername) + LINE_SEPARATOR);
    }
  }

  public void login(ChannelRepository repository, Channel channel) {
    channel.attr(USER_ATTRIBUTE_KEY).set(this);
    repository.put(this.username, channel);
  }

  public void logout(ChannelRepository channelRepository, Channel channel) {
    channel.attr(USER_ATTRIBUTE_KEY).getAndSet(null);
    channelRepository.remove(this.username);
  }
}
