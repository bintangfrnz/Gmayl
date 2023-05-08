package com.bintangfajarianto.gmayl.core.router

import android.os.Parcelable
import com.bintangfajarianto.gmayl.core.RouteDestination

sealed class HomeRouter : RouteDestination() {
    object HomePage : HomeRouter()
    data class DetailMailPage(val mail: Parcelable) : HomeRouter()
    data class SendMailPage(val user: Parcelable) : HomeRouter()
}
