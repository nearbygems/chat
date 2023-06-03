package kz.nearbygems.chat.provider.impl

import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.provider.CommandProvider
import kz.nearbygems.chat.service.ChatService
import org.springframework.stereotype.Component

@Component
class ListCommandProvider(private val service: ChatService) : CommandProvider {

    override fun type(): Command.Type = Command.Type.LIST

    override fun execute(command: Command) {
        service.sendChatNames(command.ctx)
    }

}
