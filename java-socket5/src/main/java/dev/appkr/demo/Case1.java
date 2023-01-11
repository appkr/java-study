package dev.appkr.demo;

import static dev.appkr.demo.GlobalConstants.*;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Case1 {

  public static void main(String[] args) throws IOException, InterruptedException {
    // Start server
    final EchoServer server = new EchoServer();;
    new Thread(() -> {
      try {
        server.start(PORT);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
    Thread.sleep(5_000);

    // Run client
    final EchoClient client = new EchoClient();
    client.startConnection(HOST, PORT, TIMEOUT);
    final String response = client.sendMessage("Hello");
    log.info("A message received: {}", response);

    // Close clientSocket
    server.closeClientSocket(client.getPort());

    // Stop server
    Thread.sleep(5_000);
    server.stop();
  }
}
