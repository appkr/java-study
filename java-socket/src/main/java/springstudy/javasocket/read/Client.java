package springstudy.javasocket.read;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {

    public void runClient(String ip, int port) throws IOException {
        final Socket socket = new Socket(ip, port);
        System.out.println("Connected to the server...");

        final DataInputStream in = new DataInputStream(System.in);
        final DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        char type = 's';
        final String data = "The Old Man and the Sea is a short novel written by the American author Ernest Hemingway in 1951 in Cuba, and published in 1952. It was the last major work of fiction by Hemingway that was published during his lifetime. One of his most famous works, it tells the story of Santiago, an aging Cuban fisherman who struggles with a giant marlin far out in the Gulf Stream off the coast of Cuba. In 1953, The Old Man and the Sea was awarded the Pulitzer Prize for Fiction, and it was cited by the Nobel Committee as contributing to their awarding of the Nobel Prize in Literature to Hemingway in 1954.";
        int length = data.length();
        final byte[] dataInBytes = data.getBytes(StandardCharsets.UTF_8);

        //Sending data in TLV format
        out.writeChar(type);
        out.writeInt(length);
        out.write(dataInBytes);
    }

    public static void main(String[] args) throws IOException {
        final Client client = new Client();
        client.runClient("127.0.0.1", 8888);
    }
}
