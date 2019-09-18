package springstudy.javasocket.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class InputThread extends Thread {

    private Socket socket;
    private BufferedReader r;

    public InputThread(Socket socket, BufferedReader r) {
        this.socket = socket;
        this.r = r;
    }

    public void run() {
        try {
            String line = null;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            //
        }
    }
}
