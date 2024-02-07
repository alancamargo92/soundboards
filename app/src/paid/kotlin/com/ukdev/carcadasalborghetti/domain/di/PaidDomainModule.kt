package com.ukdev.carcadasalborghetti.domain.di

import com.ukdev.carcadasalborghetti.domain.usecase.GetAvailableOperationsUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetAvailableOperationsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class PaidDomainModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetAvailableOperationsUseCase(
        impl: GetAvailableOperationsUseCaseImpl
    ): GetAvailableOperationsUseCase
}
