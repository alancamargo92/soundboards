package com.ukdev.carcadasalborghetti.di

import com.ukdev.carcadasalborghetti.handlers.AudioHandler
import com.ukdev.carcadasalborghetti.handlers.VideoHandler
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.CrashReportManagerImpl
import com.ukdev.carcadasalborghetti.view.ViewLayer
import com.ukdev.carcadasalborghetti.viewmodel.MediaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = arrayListOf(handlers(), helpers(), viewModel())

private fun viewModel() = module {
    viewModel { MediaViewModel(get()) }
}

private fun handlers() = module {
    factory { (callback: MediaCallback, view: ViewLayer) ->
        AudioHandler(androidContext(), callback, view, get())
    }

    factory { (callback: MediaCallback, view: ViewLayer) ->
        VideoHandler(androidContext(), callback, view, get())
    }
}

private fun helpers() = module {
    factory<CrashReportManager> { CrashReportManagerImpl() }
}