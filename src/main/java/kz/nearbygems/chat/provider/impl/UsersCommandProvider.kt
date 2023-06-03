package kz.nearbygems.chat.provider.impl

import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.provider.CommandProvider
import kz.nearbygems.chat.service.ChannelGroupService
import org.springframework.stereotype.Component

@Component
class UsersCommandProvider(private val service: ChannelGroupService) : CommandProvider {

    override fun type(): Command.Type = Command.Type.USERS

    override fun execute(command: Command) {
        service.sendChatUsers(command.ctx)
    }

}
