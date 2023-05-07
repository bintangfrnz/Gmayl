package com.bintangfajarianto.gmayl.data.datasource.home

import com.bintangfajarianto.gmayl.data.database.MailDatabase
import com.bintangfajarianto.gmayl.data.model.home.InboxMail
import com.bintangfajarianto.gmayl.data.model.home.SentMail
import com.bintangfajarianto.gmayl.data.repository.home.HomeStorageRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList

@OptIn(FlowPreview::class)
internal class HomeStorageDataSource(
    private val mailDatabase: MailDatabase,
) : HomeStorageRepository {
    override suspend fun getInboxMails(): List<InboxMail> =
        mailDatabase.inboxMailDao.getInboxMails().flatMapConcat { it.asFlow() }.toList()

    override suspend fun getSentMails(): List<SentMail> =
        mailDatabase.sentMailDao.getSentMails().flatMapConcat { it.asFlow() }.toList()
}
