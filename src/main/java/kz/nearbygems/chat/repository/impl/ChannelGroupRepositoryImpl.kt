package kz.nearbygems.chat.repository.impl

import kz.nearbygems.chat.db.Database
import kz.nearbygems.chat.model.Chat
import kz.nearbygems.chat.repository.ChannelGroupRepository
import org.springframework.stereotype.Repository

@Repository
class ChannelGroupRepositoryImpl(private val database: Database) : ChannelGroupRepository {

    override fun getByName(chatName: String): Chat? = database.groups[chatName]

    override fun save(chatName: String, chat: Chat) {
        database.groups[chatName] = chat
    }

    override fun getChannelGroupNames(): List<String> = database.groups.keys.sorted()

}