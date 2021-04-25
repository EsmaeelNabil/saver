package com.esmaeel.saver.di

import com.esmaeel.saver.ui.homeScreen.AppRepository
import org.koin.dsl.module


val repositoriesModule = module {
    single { AppRepository(get(), get()) }
}