package dev.appkr.demo.tcp.visitor;

import dev.appkr.demo.tcp.Packet;

public interface Parser {

  void parse(Packet parseable);
}
