package com.bintangfajarianto.gmayl.data.model.home

import android.os.Parcelable
import com.bintangfajarianto.gmayl.data.model.auth.User
import kotlinx.datetime.LocalDate

interface Mail : Parcelable {
    val id: Int?
    val sender: User
    val receiver: User
    val subject: String
    val body: String
    val sentTime: String

    fun getSentTimeAsLocalDate(): LocalDate
}
