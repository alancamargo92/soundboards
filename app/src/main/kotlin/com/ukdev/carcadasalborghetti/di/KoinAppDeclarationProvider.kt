package com.ukdev.carcadasalborghetti.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.KoinAppDeclaration

abstract class KoinAppDeclarationProvider {

    fun provideAppDeclaration(application: Application): KoinAppDeclaration = {
        androidContext(application)
        loadModules()
    }

    protected open fun loadModules() {

    }

}