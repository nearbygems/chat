package kz.nearbygems.chat.service.impl

import kz.nearbygems.chat.repository.ChatRepository
import kz.nearbygems.chat.service.ChatService
import org.springframework.stereotype.Service

@Service
class ChatServiceImpl(private val repository: ChatRepository) : ChatService {

    override fun getChatNameByUsername(username: String): String? =
        repository.getChatNameByUsername(username)

    override fun getUsernamesByChatName(chatName: String): List<String> =
        repository.getUsernamesByChatName(chatName)

    override fun saveChatName(username: String, chatName: String) {
        repository.saveChatName(username, chatName)
    }

    override fun deleteChatName(username: String) {
        repository.deleteChatName(username)
    }

}