package springstudy.javasocket;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GreetingTest {

    @Test
    public void aClientCanSendMessageAndServerCanRespond() throws IOException {
        final GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1", 8888);
        final String response = client.sendMessage("hello server");
        System.out.println(response);
        assertEquals("hello client", response);
    }
}
