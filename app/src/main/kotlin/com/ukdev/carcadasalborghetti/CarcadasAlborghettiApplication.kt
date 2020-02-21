package com.ukdev.carcadasalborghetti

import android.app.Application
import com.google.firebase.FirebaseApp
import com.ukdev.carcadasalborghetti.di.modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("registered")
open class CarcadasAlborghettiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

    protected fun startDependencyInjection() {
        startKoin {
            androidContext(this@CarcadasAlborghettiApplication)
            modules(modules)
        }
    }

}