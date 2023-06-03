package kz.nearbygems.chat.repository.impl

import io.netty.channel.group.ChannelGroup
import kz.nearbygems.chat.repository.ChannelGroupRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class ChannelGroupRepositoryImpl : ChannelGroupRepository {

    private val chats = ConcurrentHashMap<String, ChannelGroup>()

    override fun findByName(chatName: String): ChannelGroup? = chats[chatName]

    override fun save(chatName: String, chat: ChannelGroup) {
        chats[chatName] = chat
    }

    override fun getChannelGroupNames(): List<String> = chats.keys.sorted()

}