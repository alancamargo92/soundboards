package com.ukdev.carcadasalborghetti.data.di

import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.repository.MediaRepository
import com.ukdev.carcadasalborghetti.data.repository.MediaRepositoryImpl
import com.ukdev.carcadasalborghetti.di.LayerModule
import com.ukdev.carcadasalborghetti.framework.local.MediaLocalDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object FreeDataModule : LayerModule() {

    override val module = module {
        factory<MediaRepository> {
            MediaRepositoryImpl(
                    crashReportManager = get(),
                    localDataSource = get()
            )
        }
        factory<MediaLocalDataSource> { MediaLocalDataSourceImpl(context = androidContext()) }
    }

}