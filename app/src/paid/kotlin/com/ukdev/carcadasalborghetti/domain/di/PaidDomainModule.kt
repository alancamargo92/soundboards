package com.ukdev.carcadasalborghetti.domain.di

import com.ukdev.carcadasalborghetti.domain.usecase.GetAvailableOperationsUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetAvailableOperationsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
abstract class PaidDomainModule {

    @Binds
    @FragmentScoped
    abstract fun bindGetAvailableOperationsUseCase(
        impl: GetAvailableOperationsUseCaseImpl
    ): GetAvailableOperationsUseCase
}
