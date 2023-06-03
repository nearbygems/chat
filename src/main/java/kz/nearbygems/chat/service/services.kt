package kz.nearbygems.chat.service

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelId
import kz.nearbygems.chat.model.Credentials
import kz.nearbygems.chat.model.User

interface HandlerContextService {

    fun handle(ctx: ChannelHandlerContext, msg: String)

}

interface UserService {

    fun login(ctx: ChannelHandlerContext, credentials: Credentials): User

    fun save(user: User)

    fun getUserByChannel(channelId: ChannelId): User?

    fun getUsersByChatName(chatName: String): List<String>

}

interface ChatService {

    fun sendChatUsers(ctx: ChannelHandlerContext)

    fun sendChatNames(ctx: ChannelHandlerContext)

    fun sendMessage(ctx: ChannelHandlerContext, message: String)

    fun joinChat(ctx: ChannelHandlerContext, chatName: String)

    fun leaveChat(ctx: ChannelHandlerContext)

}