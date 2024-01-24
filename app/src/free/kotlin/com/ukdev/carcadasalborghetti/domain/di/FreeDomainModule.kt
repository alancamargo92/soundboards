package com.ukdev.carcadasalborghetti.domain.di

import com.ukdev.carcadasalborghetti.data.repository.MediaRepositoryImpl
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import com.ukdev.carcadasalborghetti.domain.usecase.GetAvailableOperationsUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetAvailableOperationsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract  class FreeDomainModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMediaRepository(impl: MediaRepositoryImpl): MediaRepository

    @Binds
    @ViewModelScoped
    abstract fun bindGetAvailableOperationsUseCase(
        impl: GetAvailableOperationsUseCaseImpl
    ): GetAvailableOperationsUseCase
}
