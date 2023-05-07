package com.bintangfajarianto.gmayl.data.model.home

import android.os.Parcelable
import kotlinx.datetime.LocalDate

interface Mail : Parcelable {
    val id: Int?
    val sender: String
    val subject: String
    val body: String
    val sentTime: String
    val avatarUrl: String

    fun getSentTimeAsLocalDate(): LocalDate
}
