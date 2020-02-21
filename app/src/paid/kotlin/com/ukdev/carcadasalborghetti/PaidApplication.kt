package com.ukdev.carcadasalborghetti

import com.ukdev.carcadasalborghetti.di.modules
import com.ukdev.carcadasalborghetti.repository.MediaRepository
import com.ukdev.carcadasalborghetti.repository.MediaRepositoryImpl
import org.koin.dsl.module

@Suppress("unused")
class PaidApplication : CarcadasAlborghettiApplication() {

    private val data = module {
        factory<MediaRepository> { MediaRepositoryImpl(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        modules.add(data)
        startDependencyInjection()
    }

}