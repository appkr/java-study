package app;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class App {

    private static final String ip = "127.0.0.1";
    private static final int port = 11501;

    private SSLSocket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public static void main(String[] args) throws IOException {
        System.setProperty("javax.net.ssl.trustStore", System.getProperty("user.dir") + "/cacert.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "secret");
        System.setProperty("javax.net.debug", "ssl");

        final App client = new App();
        client.startConnection(ip, port);

        final String exampleReq = "0123456789abcdefKIS0123456789PAC20190101010203FZhZwuGUGm5mPdA2mM366IdCW3v/8Vk/R1ZD/R4ESYQ=                                                        0123456789ab201901011000      100       100       12ICBC01234567890123456789DC001                100       001234567890   01234567890123456789Nfilter here                                                                                         ";

        client.sendMessage(exampleReq);
    }

    public void startConnection(String ip, int port) throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        clientSocket = (SSLSocket) factory.createSocket(ip, port);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
    }

    public byte[] sendMessage(String msg) throws IOException {
        log("rawReqBody: " + msg);

        // @see https://stackoverflow.com/a/17573179/4737224
        byte[] reqByte = msg.getBytes();
        int reqLength = reqByte.length;

        log("reqLength: " + reqLength);
        log("reqByte: " + Arrays.toString(reqByte));

        out.write(String.format("%04d", reqLength).getBytes());
        out.write(reqByte, 0, reqLength);

        byte[] resHeaderByte = new byte[4];
        in.read(resHeaderByte, 0, 4);
        int resLength = Integer.parseInt(new String(resHeaderByte, StandardCharsets.UTF_8));

        byte[] resByte = null;
        if (resLength > 0) {
            resByte = new byte[resLength];
            in.read(resByte, 0, resLength);
        }

        log("resLength: " + resLength);
        log("resByte: " + Arrays.toString(resByte));
        log("rawResBody: " + new String(resByte));

        return resByte;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    private void log(Object o) {
        System.out.println(LocalDateTime.now().toString() + " - " + o.toString());
        System.out.println("---");
    }
}
