package com.bintangfajarianto.gmayl.data.datasource.home

import com.bintangfajarianto.gmayl.data.database.MailDatabase
import com.bintangfajarianto.gmayl.data.model.home.InboxMail
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.data.model.home.SentMail
import com.bintangfajarianto.gmayl.data.python.BystarBlockCipher
import com.bintangfajarianto.gmayl.data.python.DigitalSign
import com.bintangfajarianto.gmayl.data.repository.home.HomeStorageRepository
import kotlinx.coroutines.flow.first

internal class HomeStorageDataSource(
    private val mailDatabase: MailDatabase,
    private val bystarBlockCipher: BystarBlockCipher,
    private val digitalSign: DigitalSign,
) : HomeStorageRepository {
    override suspend fun getInboxMails(): List<InboxMail> =
        mailDatabase.inboxMailDao.getInboxMails().first()

    override suspend fun getSentMails(): List<SentMail> =
        mailDatabase.sentMailDao.getSentMails().first()

    override suspend fun sendMail(
        mail: Mail,
        privateKey: String,
        symmetricKey: String,
    ) {
        var formattedBody = mail.body
        var newSignature = mail.signature

        if (symmetricKey.isNotBlank()) {
            formattedBody = bystarBlockCipher.encryptMessage(mail.body, symmetricKey)
        }

        if (privateKey.isNotBlank()) {
            val signature = digitalSign.sign(privateKey, mail.body)
            formattedBody = "$formattedBody\n\n${formatDigitalSignature(signature.second.toString())}"
            newSignature = signature
        }

        mailDatabase.sentMailDao.addMailToSent(
            SentMail(mail).copy(body = formattedBody, signature = newSignature),
        )

        val isReceive = mail.sender.email == mail.receiver.email
        if (isReceive) {
            mailDatabase.inboxMailDao.addMailToInbox(
                InboxMail(mail).copy(body = formattedBody, signature = newSignature),
            )
        }
    }

    override suspend fun deleteInboxMail(mail: InboxMail) {
        mailDatabase.inboxMailDao.deleteMailFromInbox(mail)
    }

    override suspend fun deleteSentMail(mail: SentMail) {
        mailDatabase.sentMailDao.deleteMailFromSent(mail)
    }

    companion object {
        private const val DIGITAL_SIGNATURE_TAG = "<ds>"
        private fun formatDigitalSignature(ds: String): String =
            "$DIGITAL_SIGNATURE_TAG\n$ds\n$DIGITAL_SIGNATURE_TAG"
    }
}
