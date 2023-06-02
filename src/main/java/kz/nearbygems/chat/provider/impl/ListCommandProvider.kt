package kz.nearbygems.chat.provider.impl

import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.provider.CommandProvider
import org.springframework.stereotype.Component

@Component
class ListCommandProvider : CommandProvider {

    override fun type(): Command.Type = Command.Type.LIST

}
