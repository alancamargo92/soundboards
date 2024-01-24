package com.ukdev.carcadasalborghetti

import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FreeApplication : CarcadasAlborghettiApplication() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}
