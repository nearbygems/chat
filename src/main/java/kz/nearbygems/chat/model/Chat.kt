package kz.nearbygems.chat.model

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelId
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.ImmediateEventExecutor
import kz.nearbygems.chat.exceptions.ClientLimitExceededException

class Chat {

    private val messages = mutableListOf<Message>()
    private val group = DefaultChannelGroup(ImmediateEventExecutor.INSTANCE)

    fun send(message: Message) {
        messages.add(message)
        group.writeAndFlush(message.lineText())
    }

    fun add(ctx: ChannelHandlerContext, limit: Int) {
        if (group.size > limit) {
            throw ClientLimitExceededException()
        }
        group.add(ctx.channel())
    }

    fun del(channelIds: Set<ChannelId>) {
        group.removeIf { channelIds.contains(it.id()) }
    }

    fun lastMessages(n: Int) = messages.sortedBy { it.createdTime }
        .map { it.lineText() }
        .takeLast(n)
        .joinToString(separator = "", prefix = "Last $n messages:\n", postfix = "\n")

}