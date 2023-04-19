package com.bintangfajarianto.gmayl.data.home

import com.bintangfajarianto.gmayl.extension.getLocalDate
import com.bintangfajarianto.gmayl.extension.parseIsoInstant
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageItemResponse(
    override val sender: String,
    override val subject: String,
    override val body: String,
    override val sentTime: String,
    override val imageUrl: String,
) : MessageItem {
    override fun getSentTimeAsLocalDate(): LocalDate =
        sentTime.parseIsoInstant()?.getLocalDate() ?: Clock.System.now().getLocalDate()
}
