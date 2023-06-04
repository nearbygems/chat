package kz.nearbygems.chat.repository.impl

import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class ChatRepositoryImplTest : RepositoryTest() {

    private val repository = ChatRepositoryImpl(database)

    @Test
    fun `save chat name`() {

        val username = "some username"
        val chatName = "some chat name"

        //
        repository.saveChatName(username, chatName)
        //

        assertThat(database.chats).containsEntry(username, chatName)
    }

    @Test
    fun `delete chat name`() {

        val username = "some username"
        val chatName = "some chat name"

        database.chats[username] = chatName

        //
        repository.deleteChatName(username)
        //

        assertThat(database.chats).doesNotContainEntry(username, chatName)
    }

    @Test
    fun `get chat name by username`() {

        val username = "some username"
        val chatName = "some chat name"
        database.chats[username] = chatName

        //
        val savedChatName = repository.getChatNameByUsername(username)
        //

        assertThat(savedChatName).isNotNull
        assertThat(savedChatName).isEqualTo(chatName)
    }

    @Test
    fun `get usernames by chat name`() {

        val chatName = "some chat name"
        val anotherChatName = "another chat name"

        val firstUsername = "first username"
        val secondUsername = "second username"
        val thirdUsername = "third username"

        database.chats[firstUsername] = chatName
        database.chats[secondUsername] = chatName
        database.chats[thirdUsername] = anotherChatName

        //
        val savedUsernames = repository.getUsernamesByChatName(chatName)
        //

        assertThat(savedUsernames).isNotEmpty
        assertThat(savedUsernames).contains(firstUsername, secondUsername)
    }

}