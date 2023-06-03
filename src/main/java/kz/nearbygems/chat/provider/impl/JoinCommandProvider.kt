package kz.nearbygems.chat.provider.impl

import kz.nearbygems.chat.exceptions.IncorrectJoinMessage
import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.provider.CommandProvider
import kz.nearbygems.chat.service.ChannelGroupService
import org.springframework.stereotype.Component

@Component
class JoinCommandProvider(private val service: ChannelGroupService) : CommandProvider {

    override fun type(): Command.Type = Command.Type.JOIN

    override fun execute(command: Command) {

        validate(command)

        command.message?.let { service.joinChat(command.ctx, it) }

    }

    private fun validate(command: Command) {
        if (command.message?.contains("\\s+".toRegex()) != false) {
            throw IncorrectJoinMessage()
        }
    }

}
