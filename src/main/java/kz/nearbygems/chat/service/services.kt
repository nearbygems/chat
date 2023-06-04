package kz.nearbygems.chat.service

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelId
import kz.nearbygems.chat.model.User

interface HandlerContextService {

    /**
     * Processes the message
     *
     * @param ctx channel context
     * @param msg received message
     */
    fun handle(ctx: ChannelHandlerContext, msg: String)

}

interface UserService {

    /**
     * Checks is user exists or creates new one
     *
     * @param ctx channel context
     * @param user username and password [User]
     */
    fun login(ctx: ChannelHandlerContext, user: User)

    /**
     * Saves user
     *
     * @param user username and password [User]
     */
    fun save(user: User)

}

interface ChannelService {

    /**
     * Gets username in current channel
     *
     * @param channelId channel id [ChannelId]
     * @return username
     */
    fun getUsernameByChannelId(channelId: ChannelId): String?

    /**
     * Gets channel ids by username
     *
     * @param username username
     * @return channel ids [ChannelId]
     */
    fun getChannelIdsByUsername(username: String): Set<ChannelId>

    /**
     * Saves channel id and username
     *
     * @param channelId channel id [ChannelId]
     * @param username username
     */
    fun saveChannel(channelId: ChannelId, username: String)

    /**
     * Removes channel
     *
     * @param channelId channel id [ChannelId]
     */
    fun deleteChannel(channelId: ChannelId)

}

interface ChatService {

    /**
     * Gets chat name by username
     *
     * @param username username
     * @return chat name
     */
    fun getChatNameByUsername(username: String): String?

    /**
     * Gets usernames in chat
     *
     * @param chatName chatName
     * @return usernames in current chat
     */
    fun getUsernamesByChatName(chatName: String): List<String>

    /**
     * Saves chat name and username
     *
     * @param username username
     * @param chatName chatName
     */
    fun saveChatName(username: String, chatName: String)

    /**
     * Removes user from chat
     *
     * @param username username
     */
    fun deleteChatName(username: String)

}

interface ChannelGroupService {

    /**
     * Sends all existing usernames in chat
     *
     * @param ctx channel context
     */
    fun sendChatUsers(ctx: ChannelHandlerContext)

    /**
     * Sends all existing chat names
     *
     * @param ctx channel context
     */
    fun sendChatNames(ctx: ChannelHandlerContext)

    /**
     * Sends message to current chat
     *
     * @param ctx channel context
     */
    fun sendMessage(ctx: ChannelHandlerContext, message: String)

    /**
     * Join to chat by chat name
     *
     * @param ctx channel context
     * @param chatName chatName
     */
    fun joinChat(ctx: ChannelHandlerContext, chatName: String)

    /**
     * Leaves from current chat
     *
     * @param ctx channel context
     */
    fun leaveChat(ctx: ChannelHandlerContext)

    /**
     * Disconnect from server
     *
     * @param ctx channel context
     */
    fun disconnect(ctx: ChannelHandlerContext)

}