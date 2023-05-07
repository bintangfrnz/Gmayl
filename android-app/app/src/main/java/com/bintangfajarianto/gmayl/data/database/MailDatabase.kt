package com.bintangfajarianto.gmayl.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bintangfajarianto.gmayl.data.model.home.InboxMail
import com.bintangfajarianto.gmayl.data.model.home.SentMail

@Database(
    entities = [InboxMail::class, SentMail::class],
    version = 1
)
abstract class MailDatabase: RoomDatabase() {

    abstract val inboxMailDao: InboxMailDao
    abstract val sentMailDao: SentMailDao

    companion object {
        const val DATABASE_NAME = "mail_db"
    }
}