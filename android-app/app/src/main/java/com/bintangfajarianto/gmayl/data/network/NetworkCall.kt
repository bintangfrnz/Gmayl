package com.bintangfajarianto.gmayl.data.network

import com.bintangfajarianto.gmayl.base.DataResult
import com.bintangfajarianto.gmayl.base.Error
import com.bintangfajarianto.gmayl.base.Success
import io.github.aakira.napier.Napier

suspend inline fun <T> networkCall(crossinline networkCall: suspend () -> T): DataResult<T> =
    try {
        Success(networkCall())
    } catch (ex: Throwable) {
        Napier.e(ex.message.orEmpty(), ex, tag ="GRPC")
        Error(ex)
    }
