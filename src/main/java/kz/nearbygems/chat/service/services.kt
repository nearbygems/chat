package kz.nearbygems.chat.service

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelId
import kz.nearbygems.chat.model.User

interface HandlerContextService {

    fun handle(ctx: ChannelHandlerContext, msg: String)

}

interface UserService {

    fun login(ctx: ChannelHandlerContext, user: User)

    fun save(user: User)

}

interface ChannelService {

    fun getUsernameByChannelId(channelId: ChannelId): String?

    fun getChannelIdsByUsername(username: String): Set<ChannelId>

    fun saveChannel(channelId: ChannelId, username: String)

    fun deleteChannel(channelId: ChannelId)

}

interface ChatService {

    fun getChatNameByUsername(username: String): String?

    fun getUsernamesByChatName(chatName: String): List<String>

    fun saveChatName(username: String, chatName: String)

    fun deleteChatName(username: String)

}

interface ChannelGroupService {

    fun sendChatUsers(ctx: ChannelHandlerContext)

    fun sendChatNames(ctx: ChannelHandlerContext)

    fun sendMessage(ctx: ChannelHandlerContext, message: String)

    fun joinChat(ctx: ChannelHandlerContext, chatName: String)

    fun leaveChat(ctx: ChannelHandlerContext)

    fun disconnect(ctx: ChannelHandlerContext)

}