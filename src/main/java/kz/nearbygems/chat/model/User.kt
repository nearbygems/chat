package kz.nearbygems.chat.model

import io.netty.channel.ChannelId

data class User(val username: String,
                val password: String,
                val channelIds: MutableSet<ChannelId>,
                var chatName: String?)
