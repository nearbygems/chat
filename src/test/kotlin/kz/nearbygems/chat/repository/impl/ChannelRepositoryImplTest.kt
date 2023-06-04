package kz.nearbygems.chat.repository.impl

import io.netty.channel.DefaultChannelId
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class ChannelRepositoryImplTest : RepositoryTest() {

    private val repository = ChannelRepositoryImpl(database)

    @Test
    fun `save channel`() {

        val username = "some username"
        val channelId = DefaultChannelId.newInstance()

        //
        repository.saveChannel(channelId, username)
        //

        assertThat(database.channels).containsEntry(channelId, username)
    }

    @Test
    fun `delete channel`() {

        val username = "some username"
        val channelId = DefaultChannelId.newInstance()

        database.channels[channelId] = username

        //
        repository.deleteChannel(channelId)
        //

        assertThat(database.channels).doesNotContainEntry(channelId, username)
    }

    @Test
    fun `get username by channel`() {

        val username = "some username"
        val channelId = DefaultChannelId.newInstance()
        database.channels[channelId] = username

        //
        val savedUsername = repository.getUsernameByChannelId(channelId)
        //

        assertThat(savedUsername).isNotNull
        assertThat(savedUsername).isEqualTo(username)
    }

    @Test
    fun `get channel by username`() {

        val username = "some username"
        val anotherUsername = "another username"

        val firstChannelId = DefaultChannelId.newInstance()
        val secondChannelId = DefaultChannelId.newInstance()
        val thirdChannelId = DefaultChannelId.newInstance()

        database.channels[firstChannelId] = username
        database.channels[secondChannelId] = username
        database.channels[thirdChannelId] = anotherUsername

        //
        val savedChannels = repository.getChannelIdsByUsername(username)
        //

        assertThat(savedChannels).isNotEmpty
        assertThat(savedChannels).contains(firstChannelId, secondChannelId)
    }

}