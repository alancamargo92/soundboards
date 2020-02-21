package com.ukdev.carcadasalborghetti

import com.ukdev.carcadasalborghetti.di.modules
import com.ukdev.carcadasalborghetti.repository.MediaRepository
import com.ukdev.carcadasalborghetti.repository.MediaRepositoryImpl
import com.ukdev.carcadasalborghetti.viewmodel.MediaViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Suppress("unused")
class PaidApplication : CarcadasAlborghettiApplication() {

    private val data = module {
        viewModel { MediaViewModel(get()) }
        factory<MediaRepository> { MediaRepositoryImpl(get()) }
    }

    override fun onCreate() {
        modules.add(data)
        super.onCreate()
    }

}