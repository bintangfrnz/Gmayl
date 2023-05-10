package com.bintangfajarianto.gmayl.data.model.home

import android.os.Parcelable
import com.bintangfajarianto.gmayl.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class DrawerItemType(val title: String, val iconId: Int) : Parcelable {
    INBOX(title = "Inbox", iconId = R.drawable.ic_inbox),
    SENT(title = "Sent", iconId = R.drawable.ic_send),
    KEY(title = "Key Generator", iconId = R.drawable.ic_key),
    LOGOUT(title = "Logout", iconId = R.drawable.ic_logout),
}
