package com.ukdev.carcadasalborghetti.data.di

import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSourceImpl
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSourceV2
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSourceV2Impl
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSourceV2
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSourceV2Impl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class FreeDataModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMediaLocalDataSource(impl: MediaLocalDataSourceImpl): MediaLocalDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindMediaRemoteDataSource(
        impl: MediaRemoteDataSourceV2Impl
    ): MediaRemoteDataSourceV2

    @Binds
    @ViewModelScoped
    abstract fun bindMediaLocalDataSource(
        impl: MediaLocalDataSourceV2Impl
    ): MediaLocalDataSourceV2
}
