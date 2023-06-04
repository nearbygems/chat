package kz.nearbygems.chat.provider.impl

import kz.nearbygems.chat.exceptions.LoginValidationException
import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.model.User
import kz.nearbygems.chat.provider.CommandProvider
import kz.nearbygems.chat.service.ChannelGroupService
import kz.nearbygems.chat.service.ChatService
import kz.nearbygems.chat.service.UserService
import kz.nearbygems.chat.util.splitBySpace
import org.springframework.stereotype.Component

@Component
class LoginCommandProvider(private val userService: UserService,
                           private val chatService: ChatService,
                           private val channelGroupService: ChannelGroupService) : CommandProvider {

    override fun type(): Command.Type = Command.Type.LOGIN

    override fun execute(command: Command) {

        validate(command)

        val loginAndPassword = command.message.splitBySpace()!!

        val user = User(loginAndPassword[0], loginAndPassword[1])

        userService.login(command.ctx, user)

        chatService.getChatNameByUsername(user.name)
            ?.let {
                command.ctx.writeAndFlush("Joining previous chat...\n")
                channelGroupService.joinChat(command.ctx, it)
            }

    }

    private fun validate(command: Command) {
        if (command.message.splitBySpace()?.size != 2) {
            throw LoginValidationException()
        }
    }

}
