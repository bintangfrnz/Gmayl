package com.bintangfajarianto.gmayl.data.home

import android.os.Parcelable
import kotlinx.datetime.LocalDate

interface MessageItem : Parcelable {
    val sender: String
    val subject: String
    val body: String
    val sentTime: String
    val imageUrl: String

    fun getSentTimeAsLocalDate(): LocalDate
}
