package kz.nearbygems.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import kz.nearbygems.chat.config.props.ExecutorProperties;
import kz.nearbygems.chat.service.HandlerContextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;


@Slf4j
@Component
public class Executor {

    private final HandlerContextService    service;
    private final ExecutorProperties       properties;
    private final ScheduledExecutorService executorService;

    private final ConcurrentMap<ChannelId, Future<?>> previousCommandsByChannel = new ConcurrentHashMap<>();

    public Executor(HandlerContextService service, ExecutorProperties properties) {
        this.service         = service;
        this.properties      = properties;
        this.executorService = Executors.newScheduledThreadPool(properties.corePoolSize(),
                                                                Executors.defaultThreadFactory());
    }

    public void submit(ChannelHandlerContext ctx, String msg) {

        final var channelId = ctx.channel().id();

        if (previousCommandsByChannel.containsKey(channelId)) {
            try {
                previousCommandsByChannel.get(channelId).get();
            } catch (Throwable e) {
                ctx.fireExceptionCaught(e);
            }
        }

        final var future = executorService.submit(() -> {
            try {
                service.handle(ctx, msg);
            } catch (Throwable e) {
                ctx.fireExceptionCaught(e);
            }
        });

        executorService.schedule(() -> future.cancel(true), properties.delay(), TimeUnit.MILLISECONDS);

        previousCommandsByChannel.put(channelId, future);
    }

    @PreDestroy
    void shutDown() {
        final var unfinishedTasks = executorService.shutdownNow();
        log.info("Unfinished tasks size: {}", unfinishedTasks.size());
    }

}
