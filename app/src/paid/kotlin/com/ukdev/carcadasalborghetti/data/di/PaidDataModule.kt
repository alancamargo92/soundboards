package com.ukdev.carcadasalborghetti.data.di

import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSourceV2
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSourceV2Impl
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSourceV2
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSourceV2Impl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
abstract class PaidDataModule {

    @Binds
    @FragmentScoped
    abstract fun bindMediaRemoteDataSourceV2(
        impl: MediaRemoteDataSourceV2Impl
    ): MediaRemoteDataSourceV2

    @Binds
    @FragmentScoped
    abstract fun bindMediaLocalDataSourceV2(
        impl: MediaLocalDataSourceV2Impl
    ): MediaLocalDataSourceV2
}
