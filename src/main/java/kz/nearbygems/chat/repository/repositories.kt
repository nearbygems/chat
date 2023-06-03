package kz.nearbygems.chat.repository

import io.netty.channel.ChannelId
import kz.nearbygems.chat.model.Chat
import kz.nearbygems.chat.model.User

interface UserRepository {

    fun exists(username: String): Boolean

    fun save(user: User)

    fun getByUsername(username: String): User

    fun getByChannelId(channelId: ChannelId): User?

    fun getUsersByChatName(chatName: String): List<String>

}

interface ChatRepository {

    fun findAll(): List<Chat>

    fun findByName(chatName: String): Chat?

    fun save(chat: Chat)

}