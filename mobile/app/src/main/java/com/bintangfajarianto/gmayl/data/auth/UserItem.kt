package com.bintangfajarianto.gmayl.data.auth

import android.os.Parcelable

interface UserItem : Parcelable {
    val name: String
    val email: String
    val avatarUrl: String
}
