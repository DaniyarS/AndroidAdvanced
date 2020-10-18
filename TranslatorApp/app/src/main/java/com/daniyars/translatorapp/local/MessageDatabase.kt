package com.daniyars.translatorapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.daniyars.translatorapp.models.MessageData
import com.daniyars.translatorapp.models.TranslateMessageData

@Database(
    entities = [MessageData::class, TranslateMessageData::class],
    version = 2,
    exportSchema = false
)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun translateMessageDao(): TranslateMessageDao

    companion object {
        var INSTANCE: MessageDatabase? = null
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("Create table if not exists `TranslateMessageData`(``)")
            }
        }

        fun getDatabase(context: Context): MessageDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MessageDatabase::class.java,
                    "message_database.db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE!!
        }
    }
}