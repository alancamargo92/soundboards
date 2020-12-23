package com.ukdev.carcadasalborghetti.ui.di

import com.ukdev.carcadasalborghetti.di.LayerModule
import com.ukdev.carcadasalborghetti.ui.ads.AdLoader
import com.ukdev.carcadasalborghetti.ui.ads.AdLoaderImpl
import com.ukdev.carcadasalborghetti.ui.media.AudioHandler
import com.ukdev.carcadasalborghetti.ui.media.VideoHandler
import com.ukdev.carcadasalborghetti.ui.media.VideoHelper
import com.ukdev.carcadasalborghetti.ui.media.VideoHelperImpl
import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider
import com.ukdev.carcadasalborghetti.ui.tools.MenuProviderImpl
import org.koin.dsl.module

object FreeUiModule : LayerModule() {

    override val module = module {
        factory<VideoHelper> { VideoHelperImpl() }
        factory<MenuProvider> { MenuProviderImpl() }
        factory {
            AudioHandler(
                    mediaHelper = get(),
                    crashReportManager = get(),
                    fileSharingHelper = get()
            )
        }
        factory {
            VideoHandler(
                    mediaHelper = get(),
                    crashReportManager = get(),
                    fileSharingHelper = get()
            )
        }
        factory<AdLoader> { AdLoaderImpl() }
    }

}