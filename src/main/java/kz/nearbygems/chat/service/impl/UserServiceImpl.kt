package kz.nearbygems.chat.service.impl

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelId
import kz.nearbygems.chat.exceptions.IncorrectLoginException
import kz.nearbygems.chat.model.Credentials
import kz.nearbygems.chat.model.User
import kz.nearbygems.chat.repository.UserRepository
import kz.nearbygems.chat.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val repository: UserRepository) : UserService {

    override fun login(ctx: ChannelHandlerContext, credentials: Credentials): User {

        if (repository.exists(credentials.username)) {

            val user = repository.getByUsername(credentials.username)

            if (user.password == credentials.password) {

                user.channelIds.add(ctx.channel().id())

                repository.save(user)

                return user

            } else throw IncorrectLoginException()

        } else {

            val user = User(credentials.username, credentials.password, mutableSetOf(ctx.channel().id()), null)

            repository.save(user)

            return user
        }

    }

    override fun save(user: User) {
        repository.save(user)
    }

    override fun getUserByChannel(channelId: ChannelId): User? = repository.getByChannelId(channelId)

    override fun getUsersByChatName(chatName: String): List<String> = repository.getUsersByChatName(chatName)

}