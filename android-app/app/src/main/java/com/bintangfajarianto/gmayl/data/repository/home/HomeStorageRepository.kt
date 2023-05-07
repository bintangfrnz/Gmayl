package com.bintangfajarianto.gmayl.data.repository.home

import com.bintangfajarianto.gmayl.data.model.home.InboxMail
import com.bintangfajarianto.gmayl.data.model.home.SentMail

interface HomeStorageRepository {
    suspend fun getInboxMails(): List<InboxMail>
    suspend fun getSentMails(): List<SentMail>
}
