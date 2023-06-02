package kz.nearbygems.chat;

import kz.nearbygems.chat.server.ChatServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ChatApplication {

    public static void main(String[] args) {
        final var chat   = SpringApplication.run(ChatApplication.class, args);
        final var server = chat.getBean(ChatServer.class);
        server.start();
    }

}
