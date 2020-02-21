package com.ukdev.carcadasalborghetti

import com.google.android.gms.ads.MobileAds
import com.ukdev.carcadasalborghetti.di.modules
import com.ukdev.carcadasalborghetti.handlers.AudioHandler
import com.ukdev.carcadasalborghetti.handlers.VideoHandler
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.repository.MediaRepository
import com.ukdev.carcadasalborghetti.repository.MediaRepositoryImpl
import com.ukdev.carcadasalborghetti.view.ViewLayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@Suppress("unused")
class FreeApplication : CarcadasAlborghettiApplication() {

    private val data = module {
        factory<MediaRepository> { MediaRepositoryImpl(get(), androidContext()) }
    }

    private val handlers = module {
        factory { (callback: MediaCallback, view: ViewLayer) ->
            AudioHandler(androidContext(), callback, view, get())
        }

        factory { (callback: MediaCallback, view: ViewLayer) ->
            VideoHandler(androidContext(), callback, view, get())
        }
    }

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        modules.add(data)
        startDependencyInjection()
    }

}