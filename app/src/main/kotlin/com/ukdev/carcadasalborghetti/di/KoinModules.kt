package com.ukdev.carcadasalborghetti.di

import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.CrashReportManagerImpl
import com.ukdev.carcadasalborghetti.utils.FileSharingHelper
import com.ukdev.carcadasalborghetti.utils.FileSharingHelperImpl
import com.ukdev.carcadasalborghetti.viewmodel.MediaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = arrayListOf(core())

private fun core() = module {
    viewModel { MediaViewModel(get()) }
    factory<CrashReportManager> { CrashReportManagerImpl() }
    factory<FileSharingHelper> { FileSharingHelperImpl(androidContext()) }
}