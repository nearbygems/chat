package kz.nearbygems.chat.provider

import kz.nearbygems.chat.model.Command
import org.springframework.stereotype.Component


@Component
class CommandProviderSelector(providers: List<CommandProvider>) {

    private val providerMap: Map<Command.Type, CommandProvider> = providers.associateBy { it.type() }

    init {
        Command.Type.values().toSet()
            .minus(providerMap.keys)
            .forEach { throw RuntimeException("No provider for command $it") }
    }

    fun select(command: Command): CommandProvider = providerMap[command.type]!!

}