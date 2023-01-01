package dev.appkr.demo.tcp.visitor;

import dev.appkr.demo.tcp.Packet;

public interface Formatter {

  void format(Packet formattable);
}
