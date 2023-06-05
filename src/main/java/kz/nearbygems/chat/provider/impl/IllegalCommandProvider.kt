package kz.nearbygems.chat.provider.impl

import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.provider.CommandProvider
import org.springframework.stereotype.Component

@Component
class IllegalCommandProvider : CommandProvider {

    private final val answer = """
        Chat does not support this command.
        Please, use "/help" to see existing commands.
        
    """.trimIndent()

    override fun type(): Command.Type = Command.Type.ILLEGAL

    override fun execute(command: Command) {
        command.ctx.writeAndFlush(answer)
    }

}
