package com.bintangfajarianto.gmayl.data.model.home

import android.os.Parcelable
import com.bintangfajarianto.gmayl.data.model.auth.User
import java.math.BigInteger
import kotlinx.datetime.LocalDate

interface Mail : Parcelable {
    val id: Int?
    val sender: User
    val receiver: User
    val subject: String
    val body: String
    val sentTime: String
    val encrypted: Boolean
    val signature: Pair<BigInteger, BigInteger>

    fun getSentTimeAsLocalDate(): LocalDate
}
