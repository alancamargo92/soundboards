package com.ukdev.carcadasalborghetti.di

import com.ukdev.carcadasalborghetti.handlers.AudioHandler
import com.ukdev.carcadasalborghetti.handlers.VideoHandler
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.CrashReportManagerImpl
import com.ukdev.carcadasalborghetti.view.ViewLayer
import org.koin.dsl.module

val modules = arrayListOf(callbacks(), helpers())

private fun callbacks() = module {
    factory { (callback: MediaCallback, view: ViewLayer) -> AudioHandler(callback, view) }
    factory { (callback: MediaCallback, view: ViewLayer) -> VideoHandler(callback, view) }
}

private fun helpers() = module {
    factory<CrashReportManager> { CrashReportManagerImpl() }
}