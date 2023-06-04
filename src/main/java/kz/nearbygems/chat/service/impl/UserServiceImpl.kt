package kz.nearbygems.chat.service.impl

import io.netty.channel.ChannelHandlerContext
import kz.nearbygems.chat.exceptions.IncorrectLoginException
import kz.nearbygems.chat.exceptions.UserAlreadyAuthException
import kz.nearbygems.chat.model.User
import kz.nearbygems.chat.repository.UserRepository
import kz.nearbygems.chat.service.ChannelService
import kz.nearbygems.chat.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val repository: UserRepository,
                      private val channelService: ChannelService) : UserService {

    override fun login(ctx: ChannelHandlerContext, user: User) {

        channelService.getUsernameByChannelId(ctx.channel().id())?.let {
            throw UserAlreadyAuthException()
        }

        repository.getByUsername(user.name)?.let {

            if (it.password != user.password) {
                throw IncorrectLoginException()
            }

            channelService.saveChannel(ctx.channel().id(), user.name)
            ctx.writeAndFlush("You successfully logged in.\n")

        } ?: run {

            repository.save(user)
            channelService.saveChannel(ctx.channel().id(), user.name)
            ctx.writeAndFlush("Welcome, ${user.name}!\n")
            ctx.writeAndFlush("You successfully registered.\n")

        }

    }

    override fun save(user: User) {
        repository.save(user)
    }

}