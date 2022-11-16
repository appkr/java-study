package dev.appkr.demo;

import dev.appkr.demo.tcp.server.TcpServer;
import dev.appkr.demo.tcp.config.TcpServerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableConfigurationProperties(TcpServerProperties.class)
public class Application {

	final TcpServer tcpServer;

	public Application(TcpServer tcpServer) {
		this.tcpServer = tcpServer;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@EventListener
	void onApplicationReadyEvent(ApplicationReadyEvent e) {
		tcpServer.start();
	}
}
