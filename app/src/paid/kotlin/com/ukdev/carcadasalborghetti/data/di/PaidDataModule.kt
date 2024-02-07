package com.ukdev.carcadasalborghetti.data.di

import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSourceV2
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSourceV2Impl
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSourceV2
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSourceV2Impl
import com.ukdev.carcadasalborghetti.data.tools.PaidFileHelper
import com.ukdev.carcadasalborghetti.data.tools.PaidFileHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class PaidDataModule {

    @Binds
    @ViewModelScoped
    abstract fun bindPaidFileHelper(impl: PaidFileHelperImpl): PaidFileHelper

    @Binds
    @ViewModelScoped
    abstract fun bindMediaRemoteDataSourceV2(
        impl: MediaRemoteDataSourceV2Impl
    ): MediaRemoteDataSourceV2

    @Binds
    @ViewModelScoped
    abstract fun bindMediaLocalDataSourceV2(
        impl: MediaLocalDataSourceV2Impl
    ): MediaLocalDataSourceV2
}
