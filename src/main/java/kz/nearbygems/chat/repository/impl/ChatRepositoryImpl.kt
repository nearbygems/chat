package kz.nearbygems.chat.repository.impl

import kz.nearbygems.chat.model.Chat
import kz.nearbygems.chat.repository.ChatRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class ChatRepositoryImpl : ChatRepository {

    private val chats = ConcurrentHashMap<String, Chat>()

    override fun findAll(): List<Chat> = chats.elements().toList()

    override fun findByName(chatName: String): Chat? = chats[chatName]

    override fun save(chat: Chat) {
        chats[chat.name] = chat
    }

}