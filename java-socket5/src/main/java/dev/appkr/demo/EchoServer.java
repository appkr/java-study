package dev.appkr.demo;

import static dev.appkr.demo.GlobalConstants.*;
import static dev.appkr.demo.TcpUtils.containsNewLine;
import static dev.appkr.demo.TcpUtils.trim;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoServer {

  ServerSocket serverSocket;
  ConcurrentHashMap<Integer, Socket> clientSockets = new ConcurrentHashMap<>();

  public void start(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    log.info("Server started at {} and waiting for connection from a client", port);

    while (true) {
      final Socket socket = serverSocket.accept();
      clientSockets.putIfAbsent(socket.getPort(), socket);

      if (socket.isConnected()) {
        log.info("A client is connected to {}", socket.getPort());
      }

      new ClientHandler(socket).start();
    }
  }

  public void closeClientSocket(int port) {
    if (clientSockets.containsKey(port)) {
      try {
        clientSockets.get(port).close();
      } catch (IOException e) {
        e.printStackTrace();
      }
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

        byte[] buffer = new byte[BUFSIZE];
        while (EOF != (reader.read(buffer))) {
          writer.write(trim(buffer));
          if (containsNewLine(buffer)) {
            break;
          }
        }

        writer.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
