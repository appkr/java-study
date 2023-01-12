package dev.appkr.demo;

import static dev.appkr.demo.GlobalConstants.HOST;
import static dev.appkr.demo.GlobalConstants.PORT;
import static dev.appkr.demo.GlobalConstants.TIMEOUT;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Case2 {

  public static void main(String[] args) throws IOException, InterruptedException {
    final EchoServer server = new EchoServer();;
    new Thread(() -> {
      try {
        server.start(PORT);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
    Thread.sleep(5_000);

    final EchoClient client = new EchoClient();
    client.startConnection(HOST, PORT, TIMEOUT);
    final String response = client.sendMessage("Hello");
    log.info("A message received: {}", response);
    client.stopConnection();

    Thread.sleep(5_000);
    server.stop();
  }
}
