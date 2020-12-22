package com.ukdev.carcadasalborghetti.ui.di

import com.ukdev.carcadasalborghetti.di.LayerModule
import com.ukdev.carcadasalborghetti.ui.media.MediaHelper
import com.ukdev.carcadasalborghetti.ui.media.MediaHelperImpl
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object UiModule : LayerModule() {

    override val module = module {
        viewModel { MediaViewModel(repository = get()) }
        factory<MediaHelper> {
            MediaHelperImpl(
                    videoHelper = get(),
                    context = androidContext()
            )
        }
    }

}