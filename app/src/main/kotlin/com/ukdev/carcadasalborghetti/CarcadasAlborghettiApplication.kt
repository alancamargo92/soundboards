package com.ukdev.carcadasalborghetti

import android.app.Application
import com.google.firebase.FirebaseApp

open class CarcadasAlborghettiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
