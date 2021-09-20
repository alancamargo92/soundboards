package com.ukdev.carcadasalborghetti.data.di

import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import com.ukdev.carcadasalborghetti.data.tools.PreferencesHelper
import com.ukdev.carcadasalborghetti.di.LayerModule
import com.ukdev.carcadasalborghetti.framework.tools.CrashReportManagerImpl
import com.ukdev.carcadasalborghetti.framework.tools.PreferencesHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class DataModule : LayerModule() {

    override val module = module {
        factory<CrashReportManager> { CrashReportManagerImpl() }
        factory<PreferencesHelper> { PreferencesHelperImpl(context = androidContext()) }
    }

}