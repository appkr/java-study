package springstudy.springintegration.messaging;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class IntegrationService {

//    @ServiceActivator(inputChannel = "integration.gateway.channel", outputChannel = "integration.gateway.channel.serviceactivator")
//    public Message<String> receiveMessage(Message<String> message) {
//        final Message<String> newMessage = MessageBuilder
//            .withPayload(message.getPayload() + " modified in integration.gateway.channel ")
//            .build();
//
//        return newMessage;
//    }

//    @ServiceActivator(inputChannel = "integration.gateway.channel.serviceactivator")
    @ServiceActivator(inputChannel = "integration.gateway.channel")
    public void anotherMessage(Message<String> message) {
        final MessageChannel replyChannel = (MessageChannel) message.getHeaders().getReplyChannel();
        final Message<String> newMessage = MessageBuilder
            .withPayload("Welcome " + message.getPayload() + " to Spring Integration")
            .build();

        replyChannel.send(newMessage);
    }
}
