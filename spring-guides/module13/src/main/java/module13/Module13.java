package module13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

@SpringBootApplication
@EnableJms // triggers the discovery of methods annotated with @JmsListener, creating the message listener container under the covers.
public class Module13 {

//    @Bean // This Bean definition is "OPTIONAL"
//    public JmsListenerContainerFactory<?> myFactory(
//        ConnectionFactory connectionFactory,
//        DefaultJmsListenerContainerFactoryConfigurer configurer) {
//
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//
//        // This provides all boot's default to this factory, including message factory
//        configurer.configure(factory, connectionFactory);
//        // You could still override some of Boot's default if necessary
//        return factory;
//    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        // The default MessageConverter is able to convert only basic types
        // (such as String, Map,  Serializable) and our Email is not Serializable
        // on purpose. We want to use Jackson and serialize the content to json
        // in text format (i.e. as a TextMessage). Spring Boot will detect the
        // presence of a MessageConverter and will associate it to both the
        // default JmsTemplate and any  JmsListenerContainerFactory created by
        // DefaultJmsListenerContainerFactoryConfigurer.
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Module13.class, args);

        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        // Send a message with a POJO - the template reuse the message converter
        System.out.println("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", new Email("info@example.com", "Hello"));
    }
}
