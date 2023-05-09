package com.bintangfajarianto.gmayl.extension

import android.util.Patterns

fun String.isEmailFormatValid(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isPasswordLengthValid(): Boolean =
    this.length in 8..100

fun String.isSymmetricKeyLengthValid(): Boolean =
    this.length == 16
