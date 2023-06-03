package kz.nearbygems.chat.service.impl

import io.netty.channel.ChannelHandlerContext
import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.provider.CommandProviderSelector
import kz.nearbygems.chat.service.HandlerContextService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class HandlerContextServiceImpl(private val commands: CommandProviderSelector) : HandlerContextService {

    override fun handle(ctx: ChannelHandlerContext, msg: String) {
        if (msg.isNotEmpty()) {
            Command.from(ctx, msg).let { commands.select(it).execute(it) }
        }
    }

}