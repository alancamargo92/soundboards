package com.ukdev.carcadasalborghetti

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.utils.AudioHandler
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Suppress("unused")
class CarcadasAlborghettiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        MobileAds.initialize(this)
        startKoin {
            androidContext(this@CarcadasAlborghettiApplication)
            modules(buildModule())
        }
    }

    private fun buildModule() = module {
        single { (callback: MediaCallback) -> AudioHandler(callback) }
    }

}