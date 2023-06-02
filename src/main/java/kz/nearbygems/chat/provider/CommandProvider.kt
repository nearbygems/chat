package kz.nearbygems.chat.provider

import kz.nearbygems.chat.model.Command

interface CommandProvider {

    fun type(): Command.Type

    fun execute(command: Command) {
        command.ctx.writeAndFlush("Executing command [${type().command}] with message [${command.message}]\n")
    }

}