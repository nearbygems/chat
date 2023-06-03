package kz.nearbygems.chat.provider.impl

import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.provider.CommandProvider
import org.springframework.stereotype.Component

@Component
class DisconnectCommandProvider : CommandProvider {

    private final val answer = """
        Closing your connection.
        Bye-Bye!
        
    """.trimIndent()

    override fun type(): Command.Type = Command.Type.DISCONNECT

    override fun execute(command: Command) {
        command.ctx.writeAndFlush(answer)
        command.ctx.close()
    }

}
