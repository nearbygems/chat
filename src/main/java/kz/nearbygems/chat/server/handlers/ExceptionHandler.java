package kz.nearbygems.chat.server.handlers;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import kz.nearbygems.chat.exceptions.ChatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof ChatException) {
            ctx.writeAndFlush(cause.getMessage());
        } else {
            log.error("Exception handler caught an exception", cause);
            ctx.writeAndFlush("Couldn't execute your message.\nPlease try later.\n");
        }
    }

}
