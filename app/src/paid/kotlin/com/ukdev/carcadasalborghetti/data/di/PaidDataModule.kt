package com.ukdev.carcadasalborghetti.data.di

import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.data.repository.MediaRepository
import com.ukdev.carcadasalborghetti.data.repository.MediaRepositoryImpl
import com.ukdev.carcadasalborghetti.di.LayerModule
import com.ukdev.carcadasalborghetti.framework.local.MediaLocalDataSourceImpl
import com.ukdev.carcadasalborghetti.framework.remote.MediaRemoteDataSourceImpl
import org.koin.dsl.module

object PaidDataModule : LayerModule() {

    override val module = module {
        factory<MediaRepository> {
            MediaRepositoryImpl(
                    crashReportManager = get(),
                    remoteDataSource = get(),
                    localDataSource = get(),
                    favouritesDatabase = get(),
                    ioHelper = get()
            )
        }
        factory<MediaLocalDataSource> { MediaLocalDataSourceImpl(fileHelper = get()) }
        factory<MediaRemoteDataSource> { MediaRemoteDataSourceImpl(apiProvider = get()) }
    }

}