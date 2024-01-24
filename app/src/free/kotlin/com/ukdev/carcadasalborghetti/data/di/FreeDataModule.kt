package com.ukdev.carcadasalborghetti.data.di

import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import com.ukdev.carcadasalborghetti.data.repository.MediaRepositoryImpl
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSourceImpl
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
    abstract fun bindMediaRepository(impl: MediaRepositoryImpl): MediaRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMediaLocalDataSource(impl: MediaLocalDataSourceImpl): MediaLocalDataSource
}
