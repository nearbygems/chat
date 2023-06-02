package kz.nearbygems.chat.provider.impl

import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.provider.CommandProvider
import org.springframework.stereotype.Component

@Component
class SendCommandProvider : CommandProvider {

    override fun type(): Command.Type = Command.Type.SEND

}
