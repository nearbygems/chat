package kz.nearbygems.chat.model

import io.netty.channel.ChannelHandlerContext
import kz.nearbygems.chat.util.firstWord
import kz.nearbygems.chat.util.isCommand
import kz.nearbygems.chat.util.message

class Command(val type: Type,
              val ctx: ChannelHandlerContext,
              val message: String?) {

    companion object {
        fun from(ctx: ChannelHandlerContext, message: String) =
            Command(Type.from(message), ctx, message.message())
    }

    enum class Type(val command: String) {

        LOGIN("/login"),
        JOIN("/join"),
        LEAVE("/leave"),
        DISCONNECT("/disconnect"),
        LIST("/list"),
        USERS("/users"),
        SEND("send"),
        ILLEGAL("illegal");

        companion object {

            fun from(message: String): Type =
                message.firstWord()
                    ?.let { word ->
                        Type.values()
                            .firstOrNull { it.command == word }
                        ?: if (word.isCommand())
                            ILLEGAL else SEND
                    }
                ?: ILLEGAL

        }

    }

}
