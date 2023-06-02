package kz.nearbygems.chat.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import kz.nearbygems.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class ChatHandler extends SimpleChannelInboundHandler<String> {

    private final ChatService service;

    private final ExecutorService executors = Executors.newFixedThreadPool(4, Executors.defaultThreadFactory());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info("Received message [{}] from channel with id [{}]", msg, ctx.channel().id());
        executors.execute(() -> service.handle(ctx, msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Channel handler caught an exception", cause);
        ctx.close();
    }

    @PreDestroy
    void shutDown() {
        final var tasks = executors.shutdownNow();
        log.info("Unfinished tasks size: {}", tasks.size());
    }

}
