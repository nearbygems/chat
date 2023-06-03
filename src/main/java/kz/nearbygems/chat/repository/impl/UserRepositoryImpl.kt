package kz.nearbygems.chat.repository.impl

import io.netty.channel.ChannelId
import kz.nearbygems.chat.model.User
import kz.nearbygems.chat.repository.UserRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepositoryImpl : UserRepository {

    private val users = ConcurrentHashMap<String, User>()

    override fun exists(username: String): Boolean = users.containsKey(username)

    override fun save(user: User) {
        users[user.username] = user
    }

    override fun getByUsername(username: String): User = users[username]!!

    override fun getByChannelId(channelId: ChannelId): User? =
        users.elements().toList()
            .find { it.channelIds.contains(channelId) }

    override fun getUsersByChatName(chatName: String): List<String>  =
        users.elements().toList()
            .filter { it.chatName == chatName }
            .map { it.username }
            .toList()


}