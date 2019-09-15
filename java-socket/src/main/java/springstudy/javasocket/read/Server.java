package springstudy.javasocket.read;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {

    public void runServer(int port) throws IOException {
        final ServerSocket server = new ServerSocket(port);
        System.out.println(String.format("[%s] Server started at pot %d. Waiting for a connection...",
                                         Thread.currentThread().getName(), port));

        final Socket socket = server.accept();
        System.out.println("Got connection from a client.");

        final DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        final char dataType = in.readChar();
        final int length = in.readInt();
        if (dataType == 's') {
            //Read String data in bytes
            final byte[] messageByte = new byte[128];
            boolean end = false;
            final StringBuilder dataString = new StringBuilder(length);
            int totalBytesRead = 0;

            while (false == end) {
                int currentBytesRead = in.read(messageByte);
                totalBytesRead += currentBytesRead;
                if (totalBytesRead <= length) {
                    dataString.append(new String(messageByte, 0, currentBytesRead, StandardCharsets.UTF_8));
                } else {
                    dataString.append(new String(messageByte, 0, length - totalBytesRead + currentBytesRead,
                                                 StandardCharsets.UTF_8));
                }

                if (dataString.length() >= length) {
                    end = true;
                }
            }

            System.out.println("Read " + length + " bytes of message from client.");
            System.out.println("Message: " + dataString);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                runServer();
            } catch (IOException e) { }
        }).start();
    }

    public static void runServer() throws IOException {
        final Server server = new Server();
        server.runServer(8888);
    }
}
