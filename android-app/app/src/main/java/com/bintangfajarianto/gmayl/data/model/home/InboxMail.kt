package com.bintangfajarianto.gmayl.data.model.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.extension.getLocalDate
import com.bintangfajarianto.gmayl.extension.parseIsoInstant
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class InboxMail(
    override val sender: User,
    override val subject: String,
    override val body: String,
    override val sentTime: String,
    @PrimaryKey(autoGenerate = true)
    override val id: Int? = null,
) : Mail {
    override fun getSentTimeAsLocalDate(): LocalDate =
        sentTime.parseIsoInstant()?.getLocalDate() ?: Clock.System.now().getLocalDate()

    constructor(mail: Mail): this(
        sender = mail.sender,
        subject = mail.subject,
        body = mail.body,
        sentTime = mail.sentTime,
        id = mail.id
    )
}
