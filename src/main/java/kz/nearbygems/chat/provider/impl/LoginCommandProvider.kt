package kz.nearbygems.chat.provider.impl

import kz.nearbygems.chat.exceptions.LoginValidationException
import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.model.Credentials
import kz.nearbygems.chat.provider.CommandProvider
import kz.nearbygems.chat.service.ChatService
import kz.nearbygems.chat.service.UserService
import kz.nearbygems.chat.util.splitBySpace
import org.springframework.stereotype.Component

@Component
class LoginCommandProvider(private val userService: UserService,
                           private val chatService: ChatService) : CommandProvider {

    override fun type(): Command.Type = Command.Type.LOGIN

    override fun execute(command: Command) {

        validate(command)

        val loginAndPassword = command.message.splitBySpace()!!

        val credentials = Credentials(loginAndPassword[0], loginAndPassword[1])

        val user = userService.login(command.ctx, credentials)

        user.chatName?.let {
            command.ctx.writeAndFlush("Joining chat: $it\n")
            chatService.joinChat(command.ctx, it)
        }

    }

    private fun validate(command: Command) {
        if (command.message.splitBySpace()?.size != 2) {
            command.ctx.fireExceptionCaught(LoginValidationException())
        }
    }

}
