package com.bintangfajarianto.gmayl.data.datasource.home

import com.bintangfajarianto.gmayl.data.database.MailDatabase
import com.bintangfajarianto.gmayl.data.model.home.InboxMail
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.data.model.home.SentMail
import com.bintangfajarianto.gmayl.data.repository.home.HomeStorageRepository
import kotlinx.coroutines.flow.first

internal class HomeStorageDataSource(
    private val mailDatabase: MailDatabase,
) : HomeStorageRepository {
    override suspend fun getInboxMails(): List<InboxMail> =
        mailDatabase.inboxMailDao.getInboxMails().first()

    override suspend fun getSentMails(): List<SentMail> =
        mailDatabase.sentMailDao.getSentMails().first()

    override suspend fun sendMail(
        mail: Mail,
        toEmail: String,
        publicKey: String,
        symmetricKey: String,
    ) {
        var formattedBody = mail.body

        // Temporary Encryption
        if (symmetricKey.isNotBlank()) {
            formattedBody = TEMP_ENCRYPTED_TAG + formattedBody
        }

        // Temporary Sign
        if (publicKey.isNotBlank()) {
            formattedBody = formattedBody + TEMP_DIGITAL_SIGNATURE_OPEN_TAG + publicKey + TEMP_DIGITAL_SIGNATURE_CLOSE_TAG
        }

        mailDatabase.sentMailDao.addMailToSent(SentMail(mail).copy(body = formattedBody))

        val isReceive = mail.sender.email == toEmail
        if (isReceive) {
            mailDatabase.inboxMailDao.addMailToInbox(InboxMail(mail).copy(body = formattedBody))
        }
    }

    override suspend fun deleteInboxMail(mail: InboxMail) {
        mailDatabase.inboxMailDao.deleteMailFromInbox(mail)
    }

    override suspend fun deleteSentMail(mail: SentMail) {
        mailDatabase.sentMailDao.deleteMailFromSent(mail)
    }

    companion object {
        private const val TEMP_ENCRYPTED_TAG = "[encrypted] "
        private const val TEMP_DIGITAL_SIGNATURE_OPEN_TAG = "\n\n<ds>\n"
        private const val TEMP_DIGITAL_SIGNATURE_CLOSE_TAG = "\n</ds>"
    }
}
