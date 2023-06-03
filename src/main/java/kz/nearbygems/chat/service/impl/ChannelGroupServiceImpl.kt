package kz.nearbygems.chat.service.impl

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.ImmediateEventExecutor
import kz.nearbygems.chat.exceptions.AuthException
import kz.nearbygems.chat.exceptions.ClientLimitExceededException
import kz.nearbygems.chat.exceptions.NoChatException
import kz.nearbygems.chat.repository.ChannelGroupRepository
import kz.nearbygems.chat.service.ChannelGroupService
import kz.nearbygems.chat.service.ChannelService
import kz.nearbygems.chat.service.ChatService
import org.springframework.stereotype.Service

@Service
class ChannelGroupServiceImpl(private val repository: ChannelGroupRepository,
                              private val channelService: ChannelService,
                              private val chatService: ChatService) : ChannelGroupService {

    override fun sendChatUsers(ctx: ChannelHandlerContext) {

        channelService.getUsernameByChannelId(ctx.channel().id())?.let { username ->

            chatService.getChatNameByUsername(username)?.let { chatName ->

                val users = chatService.getUsernamesByChatName(chatName)

                val message = users.joinToString(separator = "\n", prefix = "Users in current chat:\n", postfix = "\n")

                ctx.writeAndFlush(message)

            } ?: throw NoChatException()

        } ?: throw AuthException()

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

        } ?: throw AuthException()

    }

    override fun sendMessage(ctx: ChannelHandlerContext, message: String) {

        channelService.getUsernameByChannelId(ctx.channel().id())?.let { username ->

            chatService.getChatNameByUsername(username)?.let { chatName ->

                repository.findByName(chatName)?.writeAndFlush("[$username]: $message\n")

            } ?: throw NoChatException()

        } ?: throw AuthException()

    }

    override fun joinChat(ctx: ChannelHandlerContext, chatName: String) {

        channelService.getUsernameByChannelId(ctx.channel().id())?.let { username ->

            repository.findByName(chatName)?.let { group ->

                if (group.size > 3) {
                    throw ClientLimitExceededException()
                }

                group.add(ctx.channel())

                ctx.writeAndFlush("Group size is ${group.size}.\n")

            } ?: run {

                val group = DefaultChannelGroup(ImmediateEventExecutor.INSTANCE)
                group.add(ctx.channel())
                repository.save(chatName, group)
                ctx.writeAndFlush("Created new chat with name $chatName.\n")

            }

            chatService.saveChatName(username, chatName)
            ctx.writeAndFlush("You successfully joined to chat with name $chatName.\n")

        } ?: throw AuthException()

    }

    override fun leaveChat(ctx: ChannelHandlerContext) {

        channelService.getUsernameByChannelId(ctx.channel().id())?.let { username ->

            chatService.getChatNameByUsername(username)?.let { chatName ->

                repository.findByName(chatName)?.let { group ->
                    group.removeIf { channelService.getChannelIdsByUsername(username).contains(it.id()) }
                }

                chatService.deleteChatName(username)

                ctx.writeAndFlush("You successfully leaved from chat with name $chatName.\n")

            } ?: throw NoChatException()

        } ?: throw AuthException()

    }

    override fun disconnect(ctx: ChannelHandlerContext) {

        channelService.getUsernameByChannelId(ctx.channel().id())?.let { username ->

            chatService.getChatNameByUsername(username)?.let { chatName ->

                repository.findByName(chatName)?.let { group ->
                    group.removeIf { ctx.channel().id() == it.id() }
                }

            }

            channelService.deleteChannel(ctx.channel().id())

        } ?: throw AuthException()

    }
}