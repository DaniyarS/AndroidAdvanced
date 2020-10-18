package com.daniyars.translatorapp.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daniyars.translatorapp.models.MessageData

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: MessageData)

    @Query("SELECT * FROM message_table")
    fun getMessage(): List<MessageData>

    @Query("SELECT * FROM message_table")
    fun getSelectedMessage(): List<MessageData>
}
