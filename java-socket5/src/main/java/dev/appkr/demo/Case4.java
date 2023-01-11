package dev.appkr.demo;

import static dev.appkr.demo.GlobalConstants.HOST;
import static dev.appkr.demo.GlobalConstants.PORT;
import static dev.appkr.demo.GlobalConstants.TIMEOUT;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Case4 {

  public static void main(String[] args) throws IOException, InterruptedException {
    // Start server
    final LengthAwareServer server = new LengthAwareServer();;
    new Thread(() -> {
      try {
        server.start(PORT);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
    Thread.sleep(5_000);

    final LengthAwareClient client = new LengthAwareClient();
    client.startConnection(HOST, PORT, TIMEOUT);
    final String response = client.sendMessage("Hello");
    log.info("A message received: {}", response);

    // Stop server
    Thread.sleep(5_000);
    server.stop();
  }
}
