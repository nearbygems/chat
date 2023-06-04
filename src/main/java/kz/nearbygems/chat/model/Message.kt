package kz.nearbygems.chat.model

import java.time.LocalDateTime

data class Message(val from: String,
                   val text: String,
                   val createdTime: LocalDateTime = LocalDateTime.now()) {

    fun lineText() = "[${createdTime.hour}:${createdTime.minute}][$from]: $text\n"

}
