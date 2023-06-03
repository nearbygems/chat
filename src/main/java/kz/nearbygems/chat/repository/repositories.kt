package kz.nearbygems.chat.repository

import io.netty.channel.ChannelId
import io.netty.channel.group.ChannelGroup
import kz.nearbygems.chat.model.User

interface UserRepository {

    fun save(user: User)

    fun getByUsername(username: String): User?

}

interface ChannelRepository {

    fun getUsernameByChannelId(channelId: ChannelId): String?

    fun getChannelIdsByUsername(username: String): Set<ChannelId>

    fun saveChannel(channelId: ChannelId, username: String)

    fun deleteChannel(channelId: ChannelId)

}

interface ChatRepository {

    fun getChatNameByUsername(username: String): String?

    fun getUsernamesByChatName(chatName: String): List<String>

    fun saveChatName(username: String, chatName: String)

    fun deleteChatName(username: String)

}

interface ChannelGroupRepository {

    fun findByName(chatName: String): ChannelGroup?

    fun save(chatName: String, chat: ChannelGroup)

    fun getChannelGroupNames(): List<String>

}