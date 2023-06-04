package kz.nearbygems.chat.db

import io.netty.channel.ChannelId
import kz.nearbygems.chat.model.Chat
import kz.nearbygems.chat.model.User
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class Database {

    val groups = ConcurrentHashMap<String, Chat>()
    val channels = ConcurrentHashMap<ChannelId, String>()
    val chats = ConcurrentHashMap<String, String>()
    val users = ConcurrentHashMap<String, User>()

}