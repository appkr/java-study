package dev.appkr.demo;

import static dev.appkr.demo.GlobalConstants.HEADERSIZE;
import static dev.appkr.demo.TcpUtils.leftPad;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class LengthAwareClient {

  Socket socket;
  OutputStream writer;
  InputStream reader;

  public void startConnection(String ip, int port, int readTimeout) throws IOException {
    socket = new Socket(ip, port);
    socket.setSoTimeout(readTimeout); // read() 메서드가 블록킹할 시간
    socket.setSoLinger(true, 0);      // 소켓이 닫히면 전송되지 않은 소켓은 버린다
    socket.setTcpNoDelay(true);       // 패킷의 크기에 상관없이 가능한 한 빨리 패킷을 전송한다
    socket.setKeepAlive(true);        // 유휴 연결에 패킷을 보내 소켓을 계속 유지한다
    socket.setReuseAddress(true);     // 소켓을 닫을 때, 로컬 포트를 즉시 닫지 않고, 다른 소켓이 포트를 쓸 수 있도록 한다
    writer = new BufferedOutputStream(socket.getOutputStream());
    reader = new BufferedInputStream(socket.getInputStream());
  }

  public String sendMessage(String message) throws IOException {
    writer.write(leftPad(message.getBytes()));
    writer.write(message.getBytes());
    writer.flush();

    // read header and get body length
    byte[] buffer = new byte[HEADERSIZE];
    reader.read(buffer);
    final int msgLength = Integer.parseInt(new String(buffer));

    // read body
    buffer = new byte[msgLength];
    reader.read(buffer);

    return new String(buffer);
  }

  public void stopConnection() throws IOException {
    writer.close();
    reader.close();
    socket.close();
  }
}
