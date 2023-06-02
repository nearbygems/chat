package kz.nearbygems.chat.service.impl

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.netty.channel.ChannelHandlerContext
import kz.nearbygems.chat.model.Command
import kz.nearbygems.chat.provider.CommandProviderSelector
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class ChatServiceImplTest {

    @MockK
    lateinit var commands: CommandProviderSelector

    @InjectMockKs
    lateinit var service: ChatServiceImpl

    @Test
    fun `handle empty message`() {

        val ctx = mockk<ChannelHandlerContext>()
        val message = ""
        val command = mockk<Command>()

        //
        service.handle(ctx, message)
        //

        verify(exactly = 0) {
            commands.select(command)
        }
    }

}