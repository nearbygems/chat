package kz.nearbygems.chat.model

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelId
import io.netty.channel.group.ChannelGroup
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.ImmediateEventExecutor

class Chat(val name: String) {

    private val group: ChannelGroup = DefaultChannelGroup(name, ImmediateEventExecutor.INSTANCE)

    fun join(ctx: ChannelHandlerContext) {
        group.add(ctx.channel())
    }

    fun leave(channels: Set<ChannelId>) {
        group.removeIf { channels.contains(it.id()) }
    }

    fun sendUserMessage(username: String, msg: String) {
        group.writeAndFlush("[$username]: $msg")
    }

}
