package com.ukdev.carcadasalborghetti.framework.di

import com.ukdev.carcadasalborghetti.di.LayerModule
import com.ukdev.carcadasalborghetti.framework.tools.FileHelper
import com.ukdev.carcadasalborghetti.framework.tools.FileHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class FrameworkModule : LayerModule() {

    override val module = module {
        factory<FileHelper> { FileHelperImpl(androidContext()) }
    }

}