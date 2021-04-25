package com.esmaeel.saver.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * Created by Esmaeel Nabil on Feb, 2021
 */

@Database(
    entities = [
        CardItemEntity::class,
        UnitItemEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppLocalDatabase : RoomDatabase() {

    abstract fun localDatabaseDao(): LocalDatabaseDao

    companion object {
        fun getAppDataBase(context: Context): AppLocalDatabase {
            return Room.databaseBuilder(
                context,
                AppLocalDatabase::class.java,
                "db_name"
            ).build()
        }
    }
}

