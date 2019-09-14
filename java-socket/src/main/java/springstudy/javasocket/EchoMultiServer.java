package springstudy.javasocket;

import java.io.*;
import java.net.*;

public class EchoMultiServer {

    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started at " + port + " and waiting for connection from a client");
        while (true) {
            new EchoClientHandler(serverSocket.accept(), this).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private EchoMultiServer context;
        private PrintWriter out;
        private BufferedReader in;

        public EchoClientHandler(Socket socket, EchoMultiServer context) {
            this.clientSocket = socket;
            this.context = context;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        out.println("good bye");
                        break;
                    }
                    if ("!".equals(inputLine)) {
                        out.println("stopping server");
                        context.stop();
                        break;
                    }
                    out.println(inputLine);
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
            }
        }
    }

    public static void main(String[] args) throws IOException {
        final EchoMultiServer server = new EchoMultiServer();
        server.start(8888);
    }
}
