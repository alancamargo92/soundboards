package com.ukdev.carcadasalborghetti.di

import android.app.Application
import com.ukdev.carcadasalborghetti.data.di.DataModule
import com.ukdev.carcadasalborghetti.framework.di.FrameworkModule
import com.ukdev.carcadasalborghetti.ui.di.UiModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.KoinAppDeclaration

abstract class KoinAppDeclarationProvider {

    fun provideAppDeclaration(application: Application): KoinAppDeclaration = {
        androidContext(application)
        loadModules()
    }

    protected open fun loadModules() {
        DataModule.load()
        FrameworkModule.load()
        UiModule.load()
    }

}