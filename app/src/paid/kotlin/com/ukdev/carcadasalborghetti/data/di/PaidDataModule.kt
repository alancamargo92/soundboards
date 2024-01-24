package com.ukdev.carcadasalborghetti.data.di

import com.ukdev.carcadasalborghetti.data.repository.MediaRepository
import com.ukdev.carcadasalborghetti.data.repository.MediaRepositoryImpl
import com.ukdev.carcadasalborghetti.framework.tools.PaidFileHelper
import com.ukdev.carcadasalborghetti.framework.tools.PaidFileHelperImpl
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
    abstract fun bindMediaRepository(impl: MediaRepositoryImpl): MediaRepository

    @Binds
    @ViewModelScoped
    abstract fun bindPaidFileHelper(impl: PaidFileHelperImpl): PaidFileHelper
}