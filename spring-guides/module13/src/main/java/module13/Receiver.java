package module13;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @JmsListener(destination = "mailbox")
    public void receiveMessage(Email email) {
        System.out.println("Receiver <" + email + ">");
    }
}
