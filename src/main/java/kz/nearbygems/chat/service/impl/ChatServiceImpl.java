package kz.nearbygems.chat.service.impl;

import io.netty.channel.ChannelHandlerContext;
import kz.nearbygems.chat.model.Command;
import kz.nearbygems.chat.provider.CommandProviderSelector;
import kz.nearbygems.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final CommandProviderSelector commands;

    @Override
    public void handle(ChannelHandlerContext ctx, String msg) {

        if (msg.isEmpty()) {
            return;
        }

        final var command = Command.Companion.from(ctx, msg);

        commands.select(command)
                .execute(command);
    }

}