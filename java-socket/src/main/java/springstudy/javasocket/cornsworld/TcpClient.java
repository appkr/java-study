package springstudy.javasocket.cornsworld;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class TcpClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8888)) {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            System.out.println(dis.readUTF());
        } catch (IOException e) {
            //
        }
    }
}
