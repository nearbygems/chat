package kz.nearbygems.chat.service.impl

import io.netty.channel.ChannelId
import kz.nearbygems.chat.repository.ChannelRepository
import kz.nearbygems.chat.service.ChannelService
import org.springframework.stereotype.Service

@Service
class ChannelServiceImpl(private val repository: ChannelRepository) : ChannelService {

    override fun getUsernameByChannelId(channelId: ChannelId): String? =
        repository.getUsernameByChannelId(channelId)

    override fun getChannelIdsByUsername(username: String): Set<ChannelId> =
        repository.getChannelIdsByUsername(username)

    override fun saveChannel(channelId: ChannelId, username: String) {
        repository.saveChannel(channelId, username)
    }

    override fun deleteChannel(channelId: ChannelId) {
        repository.deleteChannel(channelId)
    }

}