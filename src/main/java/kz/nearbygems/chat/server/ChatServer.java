package kz.nearbygems.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import kz.nearbygems.chat.config.ChatProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatServer {

    private final ServerBootstrap serverBootstrap;
    private final ChatProperties  properties;

    private Channel channel;

    public void start() {
        try {
            final var channelFuture = serverBootstrap.bind(properties.getHost(), properties.getPort())
                                                     .sync();
            log.info("Netty server started on port {}", properties.getPort());
            channel = channelFuture.channel().closeFuture().sync().channel();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @PreDestroy
    public void stop() {
        Optional.ofNullable(channel)
                .ifPresent(c -> {
                    c.close();
                    c.parent().close();
                });
    }

}
