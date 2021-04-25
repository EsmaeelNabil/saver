package com.esmaeel.saver

import android.app.Application
import com.esmaeel.saver.di.generalModule
import com.esmaeel.saver.di.repositoriesModule
import com.esmaeel.saver.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppInstance : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AppInstance)
            modules(
                generalModule,
                repositoriesModule,
                viewModelModule,
            )
        }
    }
}