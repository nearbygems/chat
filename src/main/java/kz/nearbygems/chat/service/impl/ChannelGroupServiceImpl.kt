package kz.nearbygems.chat.service.impl

import io.netty.channel.ChannelHandlerContext
import kz.nearbygems.chat.config.props.ChatProperties
import kz.nearbygems.chat.exceptions.EmptyChatListException
import kz.nearbygems.chat.exceptions.UserAuthException
import kz.nearbygems.chat.model.Chat
import kz.nearbygems.chat.model.Message
import kz.nearbygems.chat.repository.ChannelGroupRepository
import kz.nearbygems.chat.service.ChannelGroupService
import kz.nearbygems.chat.service.ChannelService
import kz.nearbygems.chat.service.ChatService
import org.springframework.stereotype.Service

@Service
class ChannelGroupServiceImpl(private val repository: ChannelGroupRepository,
                              private val channelService: ChannelService,
                              private val chatService: ChatService,
                              private val properties: ChatProperties) : ChannelGroupService {

    override fun sendChatUsers(ctx: ChannelHandlerContext) {

        channelService.getUsernameByChannelId(ctx.channel().id())?.let { username ->

            chatService.getChatNameByUsername(username)?.let { chatName ->

                val users = chatService.getUsernamesByChatName(chatName)

                val message = users.joinToString(separator = "\n", prefix = "Users in current chat:\n", postfix = "\n")

                ctx.writeAndFlush(message)

            } ?: throw EmptyChatListException()

        } ?: throw UserAuthException()

    }

    override fun sendChatNames(ctx: ChannelHandlerContext) {

        channelService.getUsernameByChannelId(ctx.channel().id())?.let {

            val names = repository.getChannelGroupNames()

            val message = if (names.isEmpty()) {
                "Chat list is empty.\n"
            } else {
                names.joinToString(separator = "\n", prefix = "Existing chats:\n", postfix = "\n")
            }

            ctx.writeAndFlush(message)

        } ?: throw UserAuthException()

    }

    override fun sendMessage(ctx: ChannelHandlerContext, message: String) {

        channelService.getUsernameByChannelId(ctx.channel().id())?.let { username ->

            chatService.getChatNameByUsername(username)?.let { chatName ->

                repository.findByName(chatName)?.send(Message(username, message))

            } ?: throw EmptyChatListException()

        } ?: throw UserAuthException()

    }

    override fun joinChat(ctx: ChannelHandlerContext, chatName: String) {

        channelService.getUsernameByChannelId(ctx.channel().id())?.let { username ->

            repository.findByName(chatName)?.let { chat ->

                chat.add(ctx, properties.clients)
                ctx.writeAndFlush("You successfully joined to $chatName.\n")
                ctx.writeAndFlush(chat.lastMessages(properties.clients))

            } ?: run {

                val group = Chat(mutableListOf())
                group.add(ctx, properties.clients)

                repository.save(chatName, group)
                ctx.writeAndFlush("Created new chat with name $chatName.\n")

            }

            chatService.saveChatName(username, chatName)

        } ?: throw UserAuthException()

    }

    override fun leaveChat(ctx: ChannelHandlerContext) {

        channelService.getUsernameByChannelId(ctx.channel().id())?.let { username ->

            chatService.getChatNameByUsername(username)?.let { chatName ->

                repository.findByName(chatName)?.del(channelService.getChannelIdsByUsername(username))

                chatService.deleteChatName(username)

                ctx.writeAndFlush("You successfully leaved from $chatName.\n")

            } ?: throw EmptyChatListException()

        } ?: throw UserAuthException()

    }

    override fun disconnect(ctx: ChannelHandlerContext) {

        channelService.getUsernameByChannelId(ctx.channel().id())?.let { username ->

            chatService.getChatNameByUsername(username)?.let { chatName ->

                repository.findByName(chatName)?.del(setOf(ctx.channel().id()))

            }

            channelService.deleteChannel(ctx.channel().id())

        }

    }
}