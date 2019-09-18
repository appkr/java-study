package springstudy.javasocket.chat;

import java.io.*;
import java.net.Socket;

public class ChatClient {

    public static void main(String[] args) {

        String userName = "foo";
        boolean terminated = false;

        try (
            Socket socket = new Socket("127.0.0.1", 8888);
            PrintWriter w = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        ) {
            w.println(userName);
            w.flush();

            InputThread it = new InputThread(socket , r);
            it.start();

            System.out.println("userName was hard-coded to " + userName);
            System.out.println("To send a private message, type '/to <id> <message>'.");
            System.out.println("To quit, type '/quit'");
            System.out.println("Leave a message here.");

            String line = null;
            while ((line = keyboard.readLine()) != null) {
                w.println(line);
                w.flush();

                if (line.equals("/quit")) {
                    terminated = true;
                    break;
                }
            }

            System.out.println("\033[0;31mSession terminated!\033[0m");
        } catch (IOException e) {
            //
        }
    }
}
