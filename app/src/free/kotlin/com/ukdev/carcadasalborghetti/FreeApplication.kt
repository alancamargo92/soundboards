package com.ukdev.carcadasalborghetti

import com.google.android.gms.ads.MobileAds
import com.ukdev.carcadasalborghetti.di.modules
import com.ukdev.carcadasalborghetti.repository.MediaRepository
import com.ukdev.carcadasalborghetti.repository.MediaRepositoryImpl
import com.ukdev.carcadasalborghetti.viewmodel.MediaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Suppress("unused")
class FreeApplication : CarcadasAlborghettiApplication() {

    private val data = module {
        viewModel { MediaViewModel(get()) }
        factory<MediaRepository> { MediaRepositoryImpl(get(), androidContext()) }
    }

    override fun onCreate() {
        modules.add(data)
        super.onCreate()
        MobileAds.initialize(this)
    }

}