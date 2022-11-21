package dev.appkr.demo.tcp.visitor;

public interface Parceable {

  void accept(Parser parser);
}
