package dev.appkr.demo.chat.domain;

import io.netty.channel.Channel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChannelRepository {

  final ConcurrentMap<String, Channel> store = new ConcurrentHashMap<>();

  public void put(String key, Channel value) {
    store.put(key, value);
  }

  public Channel get(String key) {
    return store.get(key);
  }

  public void remove(String key) {
    store.remove(key);
  }

  public int size() {
    return store.size();
  }
}
