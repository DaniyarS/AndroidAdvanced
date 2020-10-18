package com.daniyars.translatorapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
data class MessageData(
    var sendedMessage: String? = null,
    var receivedMessage: String? = null,
    var selected: Boolean? = false,
    @PrimaryKey(autoGenerate = true)
    var messageId: Int? = null
)