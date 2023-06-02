package kz.nearbygems.chat.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import kz.nearbygems.chat.config.ChatProperties;
import kz.nearbygems.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;


@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class ChatHandler extends SimpleChannelInboundHandler<String> {

    private final ChatService    service;
    private final ChatProperties properties;

    private ScheduledExecutorService executor;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info("Received message [{}] from channel with id [{}]", msg, ctx.channel().id());
        final var future = executor.submit(() -> {
            try {
                service.handle(ctx, msg);
            } catch (Exception e) {
                log.error("Couldn't finish task", e);
            }
        });
        executor.schedule(() -> future.cancel(true), properties.getTaskTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Channel handler caught an exception", cause);
        ctx.writeAndFlush(String.format("Couldn't send your message because of %s \n", cause.getMessage()));
        ctx.close();
    }

    @PostConstruct
    void init() {
        executor = Executors.newScheduledThreadPool(properties.getCorePoolSize(), Executors.defaultThreadFactory());
    }

    @PreDestroy
    void shutDown() {
        final var unfinishedTasks = executor.shutdownNow();
        log.info("Unfinished tasks size: {}", unfinishedTasks.size());
    }

}
