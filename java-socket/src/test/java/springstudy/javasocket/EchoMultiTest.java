package springstudy.javasocket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EchoMultiTest {

    @Test
    public void aClient1CanSendMessageAndServerCanRespond() throws IOException {
        EchoClient client1 = new EchoClient();
        client1.startConnection("127.0.0.1", 8888);

        final String resp1 = client1.sendMessage("hello");
        final String resp2 = client1.sendMessage("world");
        final String resp3 = client1.sendMessage(".");

        System.out.println(resp1);
        System.out.println(resp2);
        System.out.println(resp3);

        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("good bye", resp3);

        client1.stopConnection();
    }

    @Test
    public void aClient2CanSendMessageAndServerCanRespond() throws IOException {
        EchoClient client2 = new EchoClient();
        client2.startConnection("127.0.0.1", 8888);

        final String resp1 = client2.sendMessage("hello");
        final String resp2 = client2.sendMessage("world");
        final String resp3 = client2.sendMessage(".");

        System.out.println(resp1);
        System.out.println(resp2);
        System.out.println(resp3);

        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("good bye", resp3);

        client2.stopConnection();
    }

    @Test
    public void aClient3CanSendStopMessageAndServerShouldStop() throws IOException {
        EchoClient client3 = new EchoClient();
        client3.startConnection("127.0.0.1", 8888);

        final String resp1 = client3.sendMessage("!");

        System.out.println(resp1);
        assertEquals("stopping server", resp1);

        client3.stopConnection();
    }
}
