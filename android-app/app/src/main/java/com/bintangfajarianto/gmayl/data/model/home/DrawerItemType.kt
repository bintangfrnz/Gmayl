package com.bintangfajarianto.gmayl.data.model.home

import com.bintangfajarianto.gmayl.R

enum class DrawerItemType(val title: String, val iconId: Int) {
    INBOX(title = "Inbox", iconId = R.drawable.ic_inbox),
    SENT(title = "Sent", iconId = R.drawable.ic_send),
    LOGOUT(title = "Logout", iconId = R.drawable.ic_logout),
}
