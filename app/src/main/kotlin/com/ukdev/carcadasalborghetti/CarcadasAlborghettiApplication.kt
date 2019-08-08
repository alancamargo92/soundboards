package com.ukdev.carcadasalborghetti

import android.app.Application
import com.google.firebase.FirebaseApp
import com.ukdev.carcadasalborghetti.handlers.AudioHandler
import com.ukdev.carcadasalborghetti.handlers.VideoHandler
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.view.ViewLayer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Suppress("unused", "registered")
open class CarcadasAlborghettiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@CarcadasAlborghettiApplication)
            modules(buildModule())
        }
    }

    private fun buildModule() = module {
        factory { (callback: MediaCallback, view: ViewLayer) -> AudioHandler(callback, view) }
        factory { (callback: MediaCallback, view: ViewLayer) -> VideoHandler(callback, view) }
    }

}