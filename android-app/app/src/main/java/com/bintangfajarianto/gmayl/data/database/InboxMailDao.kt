package com.bintangfajarianto.gmayl.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bintangfajarianto.gmayl.data.model.home.InboxMail
import kotlinx.coroutines.flow.Flow

@Dao
interface InboxMailDao {
    @Query("SELECT * FROM inboxMail")
    fun getInboxMails(): Flow<List<InboxMail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMailToInbox(mail: InboxMail)

    @Delete
    suspend fun deleteMailFromInbox(mail: InboxMail)
}
