package com.daniyars.translatorapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translate_message_table")
data class TranslateMessageData(
    var insertMessage: String? = null,
    var resultMessage: String? = null,
    @PrimaryKey(autoGenerate = true)
    var messageId: Int? = null
)