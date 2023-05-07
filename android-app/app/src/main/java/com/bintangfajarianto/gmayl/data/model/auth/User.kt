package com.bintangfajarianto.gmayl.data.model.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class User(
    val name: String = "",
    val email: String = "",
    val avatarUrl: String = "",
) : Parcelable {
    constructor(user: User) : this(
        name = user.name,
        email = user.email,
        avatarUrl = user.avatarUrl,
    )
}
