package kz.nearbygems.chat.repository.impl

import kz.nearbygems.chat.model.Chat
import kz.nearbygems.chat.repository.ChannelGroupRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class ChannelGroupRepositoryImpl : ChannelGroupRepository {

    private val chats = ConcurrentHashMap<String, Chat>()

    override fun findByName(chatName: String): Chat? = chats[chatName]

    override fun save(chatName: String, chat: Chat) {
        chats[chatName] = chat
    }

    override fun getChannelGroupNames(): List<String> = chats.keys.sorted()

}