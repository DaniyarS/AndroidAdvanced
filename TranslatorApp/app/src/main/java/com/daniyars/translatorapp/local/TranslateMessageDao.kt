package com.daniyars.translatorapp.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daniyars.translatorapp.models.TranslateMessageData
import io.reactivex.Flowable

@Dao
interface TranslateMessageDao {
    @Query("Select * from translate_message_table")
    fun getAllMessages(): Flowable<List<TranslateMessageData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: TranslateMessageData)
}