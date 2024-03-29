package kz.nearbygems.chat.server.handlers

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import io.netty.channel.embedded.EmbeddedChannel
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import kz.nearbygems.chat.server.Executor
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class ChatHandlerTest {

    @MockK
    lateinit var executor: Executor

    @InjectMockKs
    lateinit var handler: ChatHandler

    @Test
    fun `chat handler is receiving messages`() {

        val message = "message"

        justRun { executor.submit(any(), message) }

        //
        val channel = EmbeddedChannel(StringDecoder(), StringEncoder(), handler)
        //

        channel.writeInbound(message)

        verify { executor.submit(any(), message) }
    }

}