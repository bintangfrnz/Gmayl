package com.bintangfajarianto.gmayl.base

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

typealias DataResult<T> = Result<T, Throwable>

typealias Success<V> = Ok<V>

typealias Error<E> = Err<E>
