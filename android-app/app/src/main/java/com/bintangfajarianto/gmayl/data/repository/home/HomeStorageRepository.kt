package com.bintangfajarianto.gmayl.data.repository.home

import com.bintangfajarianto.gmayl.data.model.home.InboxMail
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.data.model.home.SentMail

interface HomeStorageRepository {
    suspend fun getInboxMails(): List<InboxMail>
    suspend fun getSentMails(): List<SentMail>
    suspend fun sendMail(
        mail: Mail,
        publicKey: String = "",
        symmetricKey: String = "",
    )
    suspend fun deleteInboxMail(mail: InboxMail)
    suspend fun deleteSentMail(mail: SentMail)
    suspend fun decryptMail(hexBody: String, symmetricKey: String): String
}
