package com.ukdev.carcadasalborghetti.di

import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.CrashReportManagerImpl
import com.ukdev.carcadasalborghetti.viewmodel.MediaViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = arrayListOf(helpers(), viewModel())

private fun viewModel() = module {
    viewModel { MediaViewModel(get()) }
}

private fun helpers() = module {
    factory<CrashReportManager> { CrashReportManagerImpl() }
}