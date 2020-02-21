package com.ukdev.carcadasalborghetti

import com.google.android.gms.ads.MobileAds
import com.ukdev.carcadasalborghetti.di.modules
import com.ukdev.carcadasalborghetti.repository.MediaRepository
import com.ukdev.carcadasalborghetti.repository.MediaRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@Suppress("unused")
class FreeApplication : CarcadasAlborghettiApplication() {

    private val data = module {
        factory<MediaRepository> { MediaRepositoryImpl(get(), androidContext()) }
    }

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        modules.add(data)
        startDependencyInjection()
    }

}