package kz.nearbygems.chat.repository.impl

import kz.nearbygems.chat.model.Chat
import kz.nearbygems.chat.model.Message
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class ChannelGroupRepositoryImplTest : RepositoryTest() {

    private val repository = ChannelGroupRepositoryImpl(database)

    @Test
    fun `get chat by name`() {

        val message = Message("from", "text")
        val chatName = "name"
        val chat = Chat()
        chat.send(message)

        database.groups[chatName] = chat

        //
        val saveChat = repository.getByName(chatName)
        //

        assertThat(saveChat).isNotNull
        assertThat(saveChat!!.lastMessages(1)).isEqualTo("Last 1 messages:\n${message.lineText()}\n")
    }

    @Test
    fun `save chat`() {

        val chatName = "name"
        val chat = Chat()

        //
        repository.save(chatName, chat)
        //

        assertThat(database.groups).containsKey(chatName)
    }

    @Test
    fun `get chat names`() {

        val firstChatName = "first name"
        val firstChat = Chat()

        val secondChatName = "second name"
        val secondChat = Chat()

        database.groups[firstChatName] = firstChat
        database.groups[secondChatName] = secondChat

        //
        val chatNames = repository.getChannelGroupNames()
        //

        assertThat(chatNames).isNotEmpty
        assertThat(chatNames).contains(firstChatName, secondChatName)
    }

}