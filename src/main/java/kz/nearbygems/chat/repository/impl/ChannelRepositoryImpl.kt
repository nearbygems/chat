package kz.nearbygems.chat.repository.impl

import io.netty.channel.ChannelId
import kz.nearbygems.chat.db.Database
import kz.nearbygems.chat.repository.ChannelRepository
import org.springframework.stereotype.Repository

@Repository
class ChannelRepositoryImpl(private val database: Database) : ChannelRepository {

    override fun getUsernameByChannelId(channelId: ChannelId): String? = database.channels[channelId]

    override fun getChannelIdsByUsername(username: String): Set<ChannelId> =
            database.channels.filterValues { it == username }.keys

    override fun saveChannel(channelId: ChannelId, username: String) {
        database.channels[channelId] = username
    }

    override fun deleteChannel(channelId: ChannelId) {
        if (database.channels.containsKey(channelId)) {
            database.channels.remove(channelId)
        }
    }

}