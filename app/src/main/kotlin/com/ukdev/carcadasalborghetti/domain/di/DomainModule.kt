package com.ukdev.carcadasalborghetti.domain.di

import com.ukdev.carcadasalborghetti.data.repository.MediaRepositoryV2Impl
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepositoryV2
import com.ukdev.carcadasalborghetti.domain.usecase.GetFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetFavouritesUseCaseImpl
import com.ukdev.carcadasalborghetti.domain.usecase.GetMediaListUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetMediaListUseCaseImpl
import com.ukdev.carcadasalborghetti.domain.usecase.RemoveFromFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.RemoveFromFavouritesUseCaseImpl
import com.ukdev.carcadasalborghetti.domain.usecase.SaveToFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.SaveToFavouritesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetMediaListUseCase(impl: GetMediaListUseCaseImpl): GetMediaListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetFavouritesUseCase(impl: GetFavouritesUseCaseImpl): GetFavouritesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSaveToFavouritesUseCase(
        impl: SaveToFavouritesUseCaseImpl
    ): SaveToFavouritesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindRemoveFromFavouritesUseCase(
        impl: RemoveFromFavouritesUseCaseImpl
    ): RemoveFromFavouritesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindMediaRepository(impl: MediaRepositoryV2Impl): MediaRepositoryV2
}
