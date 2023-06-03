package kz.nearbygems.chat.server;

import io.netty.channel.ChannelHandlerContext;
import kz.nearbygems.chat.config.ChatProperties;
import kz.nearbygems.chat.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class Executor {

    private final ChatService              service;
    private final ChatProperties           properties;
    private final ScheduledExecutorService executorService;

    public Executor(ChatService service, ChatProperties properties) {
        this.service         = service;
        this.properties      = properties;
        this.executorService = Executors.newScheduledThreadPool(properties.getCorePoolSize(), Executors.defaultThreadFactory());
    }

    public void submit(ChannelHandlerContext ctx, String msg) {
        final var future = executorService.submit(() -> {
            try {
                service.handle(ctx, msg);
            } catch (Exception e) {
                log.error("Couldn't finish task", e);
            }
        });
        executorService.schedule(() -> future.cancel(true), properties.getTaskTimeout(), TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    void shutDown() {
        final var unfinishedTasks = executorService.shutdownNow();
        log.info("Unfinished tasks size: {}", unfinishedTasks.size());
    }

}
