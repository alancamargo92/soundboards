package com.ukdev.carcadasalborghetti

import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.data.repository.MediaRepository
import com.ukdev.carcadasalborghetti.data.repository.MediaRepositoryImpl
import com.ukdev.carcadasalborghetti.di.modules
import com.ukdev.carcadasalborghetti.framework.local.MediaLocalDataSourceImpl
import com.ukdev.carcadasalborghetti.framework.local.db.FavouritesDatabaseProvider
import com.ukdev.carcadasalborghetti.framework.media.*
import com.ukdev.carcadasalborghetti.framework.remote.MediaRemoteDataSourceImpl
import com.ukdev.carcadasalborghetti.framework.remote.api.BASE_URL
import com.ukdev.carcadasalborghetti.framework.remote.api.BASE_URL_DOWNLOADS
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.ApiProvider
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.IOHelper
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.TokenHelper
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.TokenHelperImpl
import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider
import com.ukdev.carcadasalborghetti.ui.tools.MenuProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@Suppress("unused")
class PaidApplication : CarcadasAlborghettiApplication() {

    private val data = module {
        factory { ApiProvider(BASE_URL, BASE_URL_DOWNLOADS, get()) }
        factory<MediaRepository> { MediaRepositoryImpl(get(), get(), get(), get(), get()) }
        factory<MediaRemoteDataSource> { MediaRemoteDataSourceImpl(get()) }
        factory<MediaLocalDataSource> { MediaLocalDataSourceImpl(get()) }
        factory { FavouritesDatabaseProvider.getInstance(androidContext()).provideDatabase() }
    }

    private val handlers = module {
        factory { AudioHandler(get(), get(), get(), get(), get()) }
        factory { VideoHandler(get(), get(), get(), get(), get()) }
        factory { FavouritesHandler(get(), get(), get(), get(), get()) }
    }

    private val helpers = module {
        factory<TokenHelper> { TokenHelperImpl() }
        factory<VideoHelper> { VideoHelperImpl(androidContext()) }
        factory { IOHelper(get()) }
        factory<MenuProvider> { MenuProviderImpl(get()) }
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