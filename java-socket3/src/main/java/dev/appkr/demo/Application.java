package dev.appkr.demo;

import dev.appkr.demo.chat.TcpServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {

	final TcpServer tcpServer;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReadyEvent(ApplicationReadyEvent e) {
		tcpServer.start();
	}

}
