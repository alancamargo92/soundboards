package com.ukdev.carcadasalborghetti.di

import com.ukdev.carcadasalborghetti.helpers.*
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.CrashReportManagerImpl
import com.ukdev.carcadasalborghetti.viewmodel.MediaViewModelFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val modules = arrayListOf(core())

private fun core() = module {
    factory { MediaViewModelFactory(get()) }
    factory<CrashReportManager> { CrashReportManagerImpl() }
    factory<FileHelper> { FileHelperImpl(androidContext()) }
    factory<MediaHelper> { MediaHelperImpl(get(), androidContext()) }
    factory<PreferencesHelper> { PreferencesHelperImpl(androidContext()) }
}