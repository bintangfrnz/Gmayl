package com.bintangfajarianto.gmayl.core

import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onClosed
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess

class RouteDestinationHandler {
    var popUpToRoute: RouteDestination? = null
    var inclusive: Boolean = true

    val channel: Channel<RouteDestination?> by lazy { Channel(capacity = 1) }

    fun sendDestination(destination: RouteDestination?) {
        if (destination == null) return

        val result = channel.trySend(destination)

        result.onSuccess {
            Napier.d("Successfully send $destination")
        }.onFailure {
            Napier.d("Failed to send $destination with exception ${it?.message}")
        }.onClosed {
            Napier.d("Failed to send $destination caused by channel is closed with exception ${it?.message}")
        }
    }
}
