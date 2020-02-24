package com.ukdev.carcadasalborghetti

import com.ukdev.carcadasalborghetti.api.BASE_URL
import com.ukdev.carcadasalborghetti.api.BASE_URL_DOWNLOADS
import com.ukdev.carcadasalborghetti.api.tools.ApiProvider
import com.ukdev.carcadasalborghetti.api.tools.TokenHelper
import com.ukdev.carcadasalborghetti.api.tools.TokenHelperImpl
import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSourceImpl
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
class PaidApplication : CarcadasAlborghettiApplication() {

    private val data = module {
        factory { ApiProvider(BASE_URL, BASE_URL_DOWNLOADS, get()) }
        factory<MediaRepository> { MediaRepositoryImpl(get(), get()) }
        factory<MediaRemoteDataSource> { MediaRemoteDataSourceImpl(get()) }
    }

    private val handlers = module {
        factory { AudioHandler(get(), get(), get(), get()) }
        factory { VideoHandler(get(), get(), get(), get()) }
    }

    private val helpers = module {
        factory<TokenHelper> { TokenHelperImpl() }
        factory<VideoHelper> { VideoHelperImpl(androidContext()) }
    }

    override fun onCreate() {
        super.onCreate()
        with(modules) {
            add(data)
            add(handlers)
            add(helpers)
        }
        startDependencyInjection()
    }

}