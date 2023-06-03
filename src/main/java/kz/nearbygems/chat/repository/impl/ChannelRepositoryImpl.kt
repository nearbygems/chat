package kz.nearbygems.chat.repository.impl

import io.netty.channel.ChannelId
import kz.nearbygems.chat.repository.ChannelRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class ChannelRepositoryImpl : ChannelRepository {

    private val channels = ConcurrentHashMap<ChannelId, String>()

    override fun getUsernameByChannelId(channelId: ChannelId): String? = channels[channelId]

    override fun getChannelIdsByUsername(username: String): Set<ChannelId> =
            channels.filterValues { it == username }.keys

    override fun saveChannel(channelId: ChannelId, username: String) {
        channels[channelId] = username
    }

    override fun deleteChannel(channelId: ChannelId) {
        if (channels.containsKey(channelId)) {
            channels.remove(channelId)
        }
    }

}