package springstudy.javasocket.sime;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("Server started at port " + 8888);

            Socket client = serverSocket.accept();
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader r = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String userName = "";

            while (true) {
                if (userName.equals("")) {
                    userName = r.readLine() + "(" + client.getInetAddress().getHostAddress() + ")";
                    System.out.println("userName saved!");
                }

                String data = r.readLine().trim();
                if (data.equals("quit")) {
                    break;
                }

                w.write(userName + " : " + data + "\n");
                w.flush();
            }

            w.close();
            r.close();
            serverSocket.close();
            client.close();
        } catch (IOException e) {
            //
        }
    }
}
