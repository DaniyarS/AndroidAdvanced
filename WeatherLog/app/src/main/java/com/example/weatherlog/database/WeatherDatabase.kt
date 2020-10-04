package com.example.weatherlog.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weatherlog.model.Weather


@Database(entities = [Weather::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {

        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }

//    inner class roomCallBack: RoomDatabase.Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            populateDbAsyncTask().
//        }
//    }
//
//    inner class populateDbAsyncTask: AsyncTask<Void, Void, Void>() {
//        override fun doInBackground(vararg params: Void?): Void? {
//            return null
//        }
//    }
}