package dev.appkr.demo.tcp.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DisposableTcpClient extends AbstractTcpClient {

  private final String host;
  private final int port;

  private Socket socket;
  private OutputStream writer;
  private BufferedReader reader;

  public DisposableTcpClient(String host, int port, Charset charset) {
    this.host = host;
    this.port = port;
    this.charset = charset;
  }

  @Override
  public void write(byte[] message) throws Exception {
    socket = createSocket(host, port, connectionTimeout, readTimeout);
    writer = new BufferedOutputStream(socket.getOutputStream());
    writer.write(message);
    writer.flush();
  }

  @Override
  public byte[] read() throws Exception {
    reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), charset));
    final String tmp = reader.readLine();

    clearResources();

    return (tmp != null) ? tmp.getBytes(charset) : new byte[]{};
  }

  private void clearResources() throws Exception {
    if (socket != null) {
      socket.shutdownOutput();
      socket.shutdownInput();
      socket.close();
    }

    if (log.isDebugEnabled()) {
      log.debug("Socket state after cleanup: socket={}, srcPort={}, connection={}, writer={}, reader={}",
          socket.getRemoteSocketAddress(), socket.getLocalPort(), socket.isConnected(), !socket.isOutputShutdown(),
          !socket.isInputShutdown());
    }

    socket = null;
  }
}
