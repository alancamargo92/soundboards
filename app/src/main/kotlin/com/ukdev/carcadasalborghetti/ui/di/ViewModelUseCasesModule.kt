package com.ukdev.carcadasalborghetti.ui.di

import com.ukdev.carcadasalborghetti.domain.usecase.GetAvailableOperationsUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetMediaListUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.RemoveFromFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.SaveToFavouritesUseCase
import com.ukdev.carcadasalborghetti.ui.viewmodel.MediaListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelUseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideMediaListViewModelUseCases(
        getMediaListUseCase: GetMediaListUseCase,
        getFavouritesUseCase: GetFavouritesUseCase,
        saveToFavouritesUseCase: SaveToFavouritesUseCase,
        removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
        getAvailableOperationsUseCase: GetAvailableOperationsUseCase
    ): MediaListViewModel.UseCases {
        return MediaListViewModel.UseCases(
            getMediaListUseCase,
            getFavouritesUseCase,
            saveToFavouritesUseCase,
            removeFromFavouritesUseCase,
            getAvailableOperationsUseCase
        )
    }
}
