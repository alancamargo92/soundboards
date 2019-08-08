package com.ukdev.carcadasalborghetti

import com.google.android.gms.ads.MobileAds

@Suppress("unused")
class FreeApplication : CarcadasAlborghettiApplication() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }

}