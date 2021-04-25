package com.esmaeel.saver.di

import com.esmaeel.saver.ui.homeScreen.AppViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AppViewModel(get(), get()) }
}