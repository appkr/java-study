package springstudy.javasocket.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8888)) {
            Map<String, Object> hm = new HashMap<>();
            while (true) {
                System.out.println("Server ready at port 8888 and waiting for a connection");
                Socket socket = server.accept();
                ChatThread chatThread = new ChatThread(socket, hm);
                chatThread.start();
            }
        } catch (IOException e) {
            //
        }
    }
}
