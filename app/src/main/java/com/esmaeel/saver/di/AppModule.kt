package com.esmaeel.saver.di

import android.content.Context
import com.esmaeel.saver.base.ContextProviders
import com.esmaeel.saver.db.AppLocalDatabase
import com.esmaeel.saver.db.LocalDatabaseDao
import org.koin.dsl.module


val generalModule = module {
    single { ContextProviders() }
    fun provideDb(context: Context): AppLocalDatabase {
        return AppLocalDatabase.getAppDataBase(context = context)
    }

    fun provideDao(appLocalDatabase: AppLocalDatabase): LocalDatabaseDao {
        return LocalDataSourceImpl(appLocalDatabase = appLocalDatabase)
    }

    single { provideDb(get()) }
    single { provideDao(get()) }
}