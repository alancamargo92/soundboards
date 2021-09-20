package com.ukdev.carcadasalborghetti.ui.di

import com.ukdev.carcadasalborghetti.di.LayerModule
import com.ukdev.carcadasalborghetti.ui.media.*
import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider
import com.ukdev.carcadasalborghetti.ui.tools.MenuProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class PaidUiModule : LayerModule() {

    override val module = module {
        factory {
            AudioHandler(
                mediaHelper = get(),
                crashReportManager = get(),
                remoteDataSource = get(),
                ioHelper = get(),
                paidFileHelper = get()
            )
        }
        factory {
            FavouritesHandler(
                mediaHelper = get(),
                crashReportManager = get(),
                remoteDataSource = get(),
                ioHelper = get(),
                paidFileHelper = get()
            )
        }
        factory {
            VideoHandler(
                mediaHelper = get(),
                crashReportManager = get(),
                remoteDataSource = get(),
                ioHelper = get(),
                paidFileHelper = get()
            )
        }
        factory<VideoHelper> { VideoHelperImpl(context = androidContext()) }
        factory<MenuProvider> { MenuProviderImpl(localDataSource = get()) }
    }

}