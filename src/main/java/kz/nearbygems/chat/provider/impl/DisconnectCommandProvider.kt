package kz.nearbygems.chat.provider.impl

import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.provider.CommandProvider
import kz.nearbygems.chat.service.ChannelGroupService
import org.springframework.stereotype.Component

@Component
class DisconnectCommandProvider(private val service: ChannelGroupService) : CommandProvider {

    private final val answer = """
        
        Closing your connection.
        Bye-Bye!
        
    """.trimIndent()

    override fun type(): Command.Type = Command.Type.DISCONNECT

    override fun execute(command: Command) {
        service.disconnect(command.ctx)
        command.ctx.writeAndFlush(answer)
        command.ctx.close()
    }

}
