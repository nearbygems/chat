package kz.nearbygems.chat.provider.impl

import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.provider.CommandProvider
import org.springframework.stereotype.Component

@Component
class HelpCommandProvider : CommandProvider {

    private final val answer = """
        Hi, it's simple text based chat.
        Chat can handle next commands:
        /login <name> <password> - login with username and password;
        /join <channel> - join channel by name, you can only join one channel at a time;
        /leave - leave current channel;
        /disconnect - close connection;
        /list - list of existing channels;
        /users - list of users in current channel;
        <text message> - send message to current channel.
        
    """.trimIndent()

    override fun type(): Command.Type = Command.Type.HELP

    override fun execute(command: Command) {
        command.ctx.writeAndFlush(answer)
    }

}