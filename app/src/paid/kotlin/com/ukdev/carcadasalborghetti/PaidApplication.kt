package com.ukdev.carcadasalborghetti

import com.ukdev.carcadasalborghetti.repository.MediaRepository
import com.ukdev.carcadasalborghetti.repository.MediaRepositoryImpl
import org.koin.dsl.module

@Suppress("unused")
class PaidApplication : CarcadasAlborghettiApplication() {

    private val repositoryModule = module {
        factory<MediaRepository> { MediaRepositoryImpl() }
    }

    override fun onCreate() {
        modules.add(repositoryModule)
        super.onCreate()
    }

}