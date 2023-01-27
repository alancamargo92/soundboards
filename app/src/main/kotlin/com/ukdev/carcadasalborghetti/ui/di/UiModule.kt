package com.ukdev.carcadasalborghetti.ui.di

import com.ukdev.carcadasalborghetti.di.LayerModule
import com.ukdev.carcadasalborghetti.ui.media.MediaHelper
import com.ukdev.carcadasalborghetti.ui.media.MediaHelperImpl
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class UiModule : LayerModule() {

    override val module = module {
        viewModel {
            MediaViewModel(
                repository = get(),
                crashReportManager = get()
            )
        }
        factory<MediaHelper> {
            MediaHelperImpl(
                    videoHelper = get(),
                    context = androidContext()
            )
        }
    }

}