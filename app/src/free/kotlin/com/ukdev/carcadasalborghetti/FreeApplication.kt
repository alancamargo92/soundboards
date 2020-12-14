package com.ukdev.carcadasalborghetti

import com.google.android.gms.ads.MobileAds
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.framework.local.MediaLocalDataSourceImpl
import com.ukdev.carcadasalborghetti.di.modules
import com.ukdev.carcadasalborghetti.framework.media.AudioHandler
import com.ukdev.carcadasalborghetti.framework.media.VideoHandler
import com.ukdev.carcadasalborghetti.framework.media.VideoHelper
import com.ukdev.carcadasalborghetti.framework.media.VideoHelperImpl
import com.ukdev.carcadasalborghetti.data.repository.MediaRepository
import com.ukdev.carcadasalborghetti.data.repository.MediaRepositoryImpl
import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider
import com.ukdev.carcadasalborghetti.ui.tools.MenuProviderImpl
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
        factory<MenuProvider> { MenuProviderImpl() }
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