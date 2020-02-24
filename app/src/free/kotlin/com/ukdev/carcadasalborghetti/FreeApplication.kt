package com.ukdev.carcadasalborghetti

import com.google.android.gms.ads.MobileAds
import com.ukdev.carcadasalborghetti.data.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.MediaLocalDataSourceImpl
import com.ukdev.carcadasalborghetti.di.modules
import com.ukdev.carcadasalborghetti.handlers.AudioHandler
import com.ukdev.carcadasalborghetti.handlers.VideoHandler
import com.ukdev.carcadasalborghetti.helpers.VideoHelper
import com.ukdev.carcadasalborghetti.helpers.VideoHelperImpl
import com.ukdev.carcadasalborghetti.repository.MediaRepository
import com.ukdev.carcadasalborghetti.repository.MediaRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@Suppress("unused")
class FreeApplication : CarcadasAlborghettiApplication() {

    private val data = module {
        factory<MediaRepository> { MediaRepositoryImpl(get(), get()) }
        factory<MediaLocalDataSource> { MediaLocalDataSourceImpl(androidContext()) }
    }

    private val handlers = module {
        factory { AudioHandler(get(), get(), get()) }
        factory { VideoHandler(get(), get(), get()) }
    }

    private val helpers = module {
        factory<VideoHelper> { VideoHelperImpl() }
    }

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        with(modules) {
            add(data)
            add(handlers)
            add(helpers)
        }
        startDependencyInjection()
    }

}