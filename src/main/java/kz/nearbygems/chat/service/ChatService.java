package kz.nearbygems.chat.service;

import io.netty.channel.ChannelHandlerContext;

public interface ChatService {

    void handle(ChannelHandlerContext ctx, String msg);

}
