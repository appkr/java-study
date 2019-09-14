package springstudy.javasocket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class EchoTest {

    private EchoClient client;

    @Test
    public void aClientCanSendMessageAndServerCanRespond() throws IOException {
        final String resp1 = client.sendMessage("hello");
        final String resp2 = client.sendMessage("world");
        final String resp3 = client.sendMessage("!");
        final String resp4 = client.sendMessage(".");

        System.out.println(resp1);
        System.out.println(resp2);
        System.out.println(resp3);
        System.out.println(resp4);

        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("!", resp3);
        assertEquals("good bye", resp4);
    }

    @Before
    public void setup() throws IOException {
        client = new EchoClient();
        client.startConnection("127.0.0.1", 8888);
    }

    @After
    public void tearDown() throws IOException {
        client.stopConnection();
    }
}
