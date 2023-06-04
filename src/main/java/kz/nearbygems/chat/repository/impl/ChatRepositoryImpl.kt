package kz.nearbygems.chat.repository.impl

import kz.nearbygems.chat.repository.ChatRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class ChatRepositoryImpl : ChatRepository {

    private val chats = ConcurrentHashMap<String, String>()

    override fun getChatNameByUsername(username: String): String? = chats[username]

    override fun getUsernamesByChatName(chatName: String): List<String> =
        chats.filterValues { it == chatName }.keys.sorted()

    override fun saveChatName(username: String, chatName: String) {
        chats[username] = chatName
    }

    override fun deleteChatName(username: String) {
        if (chats.containsKey(username)) {
            chats.remove(username)
        }
    }

}