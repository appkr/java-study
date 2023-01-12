package dev.appkr.demo;

import static dev.appkr.demo.GlobalConstants.HEADERSIZE;
import static dev.appkr.demo.GlobalConstants.PORT;
import static dev.appkr.demo.TcpUtils.leftPad;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LengthAwareServer {

  ServerSocket serverSocket;

  public void start(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    log.info("Server started at {} and waiting for connection from a client", port);

    while (true) {
      final Socket socket = serverSocket.accept();

      if (socket.isConnected()) {
        log.info("A client is connected to {}", socket.getPort());
      }

      new ClientHandler(socket).start();
    }
  }

  public void stop() throws IOException {
    serverSocket.close();
  }

  static class ClientHandler extends Thread {

    Socket socket;

    public ClientHandler(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      try {
        final InputStream reader = new BufferedInputStream(socket.getInputStream());
        final OutputStream writer = new BufferedOutputStream(socket.getOutputStream());

        // read header and get body length
        byte[] buffer = new byte[HEADERSIZE];
        reader.read(buffer);
        final int msgLength = Integer.parseInt(new String(buffer));

        // read body
        buffer = new byte[msgLength];
        reader.read(buffer);

        // write response
        writer.write(leftPad(buffer));
        writer.write(buffer);
        writer.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
