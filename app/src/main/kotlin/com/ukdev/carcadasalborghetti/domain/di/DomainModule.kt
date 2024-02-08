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
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
abstract class DomainModule {

    @Binds
    @FragmentScoped
    abstract fun bindGetMediaListUseCase(impl: GetMediaListUseCaseImpl): GetMediaListUseCase

    @Binds
    @FragmentScoped
    abstract fun bindGetFavouritesUseCase(impl: GetFavouritesUseCaseImpl): GetFavouritesUseCase

    @Binds
    @FragmentScoped
    abstract fun bindSaveToFavouritesUseCase(
        impl: SaveToFavouritesUseCaseImpl
    ): SaveToFavouritesUseCase

    @Binds
    @FragmentScoped
    abstract fun bindRemoveFromFavouritesUseCase(
        impl: RemoveFromFavouritesUseCaseImpl
    ): RemoveFromFavouritesUseCase

    @Binds
    @FragmentScoped
    abstract fun bindMediaRepository(impl: MediaRepositoryV2Impl): MediaRepositoryV2
}
