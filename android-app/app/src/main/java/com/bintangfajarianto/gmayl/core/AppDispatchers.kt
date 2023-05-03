package com.bintangfajarianto.gmayl.core

import io.github.aakira.napier.Napier
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object AppDispatchers {
    val ioDispatcher: CoroutineContext = Dispatchers.IO
}

val AppDispatchers.coroutineScope: CoroutineScope
    get() = object : CoroutineScope {
        override val coroutineContext = SupervisorJob() + ioDispatcher + exceptionHandler
    }

val exceptionHandler: CoroutineExceptionHandler
    get() = CoroutineExceptionHandler { _, throwable ->
        Napier.e(
            throwable.printStackTrace().toString(),
            throwable,
            CoroutineExceptionHandler::class.simpleName,
        )
    }
