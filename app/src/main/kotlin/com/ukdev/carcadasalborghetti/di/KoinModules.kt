package com.ukdev.carcadasalborghetti.di

import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import com.ukdev.carcadasalborghetti.data.tools.PreferencesHelper
import com.ukdev.carcadasalborghetti.framework.media.MediaHelper
import com.ukdev.carcadasalborghetti.framework.media.MediaHelperImpl
import com.ukdev.carcadasalborghetti.framework.tools.CrashReportManagerImpl
import com.ukdev.carcadasalborghetti.framework.tools.FileHelper
import com.ukdev.carcadasalborghetti.framework.tools.FileHelperImpl
import com.ukdev.carcadasalborghetti.framework.tools.PreferencesHelperImpl
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaViewModelFactory
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