package springstudy.javasocket.sime;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in, "UTF-8");

        try {
            System.out.println("What is the server IP? ");
            String ip = sc.nextLine();

            Socket socket = new Socket(InetAddress.getByName(ip), 8888);
            String userName = "";
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                if (userName.equals("")) {
                    System.out.println("What is userName? ");
                    String str = sc.next();
                    w.write(str + "\n");
                    w.flush();

                    userName = str;
                }

                System.out.println("Leave your message here: ");
                String str = sc.nextLine();
                w.write(str + "\n");
                w.flush();

                if (str.equals("quit")) {
                    break;
                }

                String data = r.readLine().trim();
                System.out.println(data);
            }

            socket.close();
            w.close();
            r.close();
        } catch (IOException e) {
            //
        }
    }
}
