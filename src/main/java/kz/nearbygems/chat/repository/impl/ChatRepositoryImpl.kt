package kz.nearbygems.chat.repository.impl

import kz.nearbygems.chat.db.Database
import kz.nearbygems.chat.repository.ChatRepository
import org.springframework.stereotype.Repository

@Repository
class ChatRepositoryImpl(private val database: Database) : ChatRepository {

    override fun getChatNameByUsername(username: String): String? = database.chats[username]

    override fun getUsernamesByChatName(chatName: String): List<String> =
            database.chats.filterValues { it == chatName }.keys.sorted()

    override fun saveChatName(username: String, chatName: String) {
        database.chats[username] = chatName
    }

    override fun deleteChatName(username: String) {
        if (database.chats.containsKey(username)) {
            database.chats.remove(username)
        }
    }

}