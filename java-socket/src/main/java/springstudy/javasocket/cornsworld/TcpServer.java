package springstudy.javasocket.cornsworld;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("Server started at 8888");

            Socket socket = serverSocket.accept();
            System.out.println("Connection established with " + socket.getInetAddress());

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF("Hello client");
        } catch (IOException e) {
            //
        }
    }
}
