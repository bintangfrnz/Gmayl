package com.bintangfajarianto.gmayl.data.model.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
class User(
    private val name: String = "",
    private val email: String = "",
    private val avatarUrl: String = "",
) : Parcelable {
    constructor(user: User) : this(
        name = user.name,
        email = user.email,
        avatarUrl = user.avatarUrl,
    )
}
