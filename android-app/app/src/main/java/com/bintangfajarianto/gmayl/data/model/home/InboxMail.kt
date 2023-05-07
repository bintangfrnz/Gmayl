package com.bintangfajarianto.gmayl.data.model.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bintangfajarianto.gmayl.extension.getLocalDate
import com.bintangfajarianto.gmayl.extension.parseIsoInstant
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class InboxMail(
    override val sender: String,
    override val subject: String,
    override val body: String,
    override val sentTime: String,
    override val avatarUrl: String,
    @PrimaryKey(autoGenerate = true)
    override val id: Int? = null,
) : Mail {
    override fun getSentTimeAsLocalDate(): LocalDate =
        sentTime.parseIsoInstant()?.getLocalDate() ?: Clock.System.now().getLocalDate()
}
