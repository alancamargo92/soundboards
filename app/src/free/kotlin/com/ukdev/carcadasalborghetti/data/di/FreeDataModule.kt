package com.ukdev.carcadasalborghetti.data.di

import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSourceImpl
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
abstract class FreeDataModule {

    @Binds
    @FragmentScoped
    abstract fun bindMediaRemoteDataSource(
        impl: MediaRemoteDataSourceImpl
    ): MediaRemoteDataSource

    @Binds
    @FragmentScoped
    abstract fun bindMediaLocalDataSource(
        impl: MediaLocalDataSourceImpl
    ): MediaLocalDataSource
}
