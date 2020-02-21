package com.ukdev.carcadasalborghetti

import android.app.Application
import com.google.firebase.FirebaseApp
import com.ukdev.carcadasalborghetti.di.modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused", "registered")
open class CarcadasAlborghettiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@CarcadasAlborghettiApplication)
            modules(modules)
        }
    }

}