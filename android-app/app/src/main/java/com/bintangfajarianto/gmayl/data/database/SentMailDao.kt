package com.bintangfajarianto.gmayl.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bintangfajarianto.gmayl.data.model.home.SentMail
import kotlinx.coroutines.flow.Flow

@Dao
interface SentMailDao {
    @Query("SELECT * FROM sentMail")
    fun getSentMails(): Flow<List<SentMail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMailToSent(mail: SentMail)

    @Delete
    suspend fun deleteMailFromSent(mail: SentMail)
}
