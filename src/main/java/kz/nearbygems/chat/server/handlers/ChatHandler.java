package kz.nearbygems.chat.server.handlers;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import kz.nearbygems.chat.server.Executor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class ChatHandler extends SimpleChannelInboundHandler<String> {

    private final Executor executor;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        ctx.channel().writeAndFlush("""
                                    Welcome to chat!
                                    Please, use /help command to find out the possibilities of the chat.
                                    """);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info("Received message [{}] from channel with id [{}]", msg, ctx.channel().id());
        executor.submit(ctx, msg);
    }

}
