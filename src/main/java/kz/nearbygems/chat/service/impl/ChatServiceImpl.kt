package kz.nearbygems.chat.service.impl

import io.netty.channel.ChannelHandlerContext
import kz.nearbygems.chat.model.Chat
import kz.nearbygems.chat.repository.ChatRepository
import kz.nearbygems.chat.service.ChatService
import kz.nearbygems.chat.service.UserService
import org.springframework.stereotype.Service

@Service
class ChatServiceImpl(private val repository: ChatRepository,
                      private val userService: UserService) : ChatService {

    override fun sendChatUsers(ctx: ChannelHandlerContext) {

        userService.getUserByChannel(ctx.channel().id())?.let {

            it.chatName?.let { chatName ->

                val chatUsers = userService.getUsersByChatName(chatName)
                    .joinToString(prefix = "User list:\n",
                                  postfix = "\n",
                                  separator = "\n")

                ctx.writeAndFlush(chatUsers)

            } ?: run { ctx.writeAndFlush("You haven't joined any chat.\n") }

        } ?: run { ctx.writeAndFlush("You haven't logged in.\n") }

    }

    override fun sendChatNames(ctx: ChannelHandlerContext) {

        val chats = repository.findAll()

        if (chats.isEmpty()) {

            ctx.writeAndFlush("There are no chats yet.\n")

        } else {

            val message = chats.joinToString(prefix = "Chat list:\n",
                                             postfix = "\n",
                                             separator = "\n") { it.name }

            ctx.writeAndFlush(message)

        }

    }

    override fun sendMessage(ctx: ChannelHandlerContext, message: String) {

        userService.getUserByChannel(ctx.channel().id())?.let {

            it.chatName?.let { chatName ->

                val chat = repository.findByName(chatName)

                chat?.sendUserMessage(it.username, message)

            }

        } ?: run { ctx.writeAndFlush("You haven't logged in.\n") }

    }

    override fun joinChat(ctx: ChannelHandlerContext, chatName: String) {

        userService.getUserByChannel(ctx.channel().id())?.let {

            repository.findByName(chatName)?.join(ctx) ?: run {

                val chat = Chat(chatName)

                repository.save(chat)

            }

        } ?: run { ctx.writeAndFlush("You haven't logged in.\n") }

    }

    override fun leaveChat(ctx: ChannelHandlerContext) {

        userService.getUserByChannel(ctx.channel().id())?.let {

            it.chatName?.let { chatName ->

                val chat = repository.findByName(chatName)

                chat?.leave(it.channelIds)

                it.chatName = null

                userService.save(it)

            }

        } ?: run { ctx.writeAndFlush("You haven't logged in.\n") }

    }

}