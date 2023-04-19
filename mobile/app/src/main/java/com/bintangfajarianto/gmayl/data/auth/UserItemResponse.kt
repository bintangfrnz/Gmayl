package com.bintangfajarianto.gmayl.data.auth

import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItemResponse(
    override val name: String,
    override val email: String,
    override val avatarUrl: String,
) : UserItem
