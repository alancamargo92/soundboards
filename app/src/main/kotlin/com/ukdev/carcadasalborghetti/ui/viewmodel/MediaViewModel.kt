package com.ukdev.carcadasalborghetti.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.di.IoDispatcher
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.Operation
import com.ukdev.carcadasalborghetti.domain.usecase.GetAvailableOperationsUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetMediaListUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.RemoveFromFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.SaveToFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val getMediaListUseCase: GetMediaListUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val saveToFavouritesUseCase: SaveToFavouritesUseCase,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    private val getAvailableOperationsUseCase: GetAvailableOperationsUseCase,
    private val logger: Logger,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    fun getMedia(mediaType: MediaType) {
        viewModelScope.launch(dispatcher) {
            getMediaListUseCase(mediaType).onStart {
                // Do stuff
            }.onCompletion {
                // Do stuff
            }.catch {
                logger.logException(it)
                // Do stuff
            }.collect {
                // Do stuff
            }
        }
    }

    fun getFavourites() {
        viewModelScope.launch(dispatcher) {
            getFavouritesUseCase().onStart {
                // Do stuff
            }.onCompletion {
                // Do stuff
            }.catch {
                logger.logException(it)
                // Do stuff
            }.collect {
                // Do stuff
            }
        }
    }

    fun getAvailableOperations(media: Media): List<Operation> {
        return getAvailableOperationsUseCase(media)
    }

    fun saveToFavourites(media: Media) {
        viewModelScope.launch(dispatcher) {
            saveToFavouritesUseCase(media).onStart {
                // Do stuff
            }.onCompletion {
                // Do stuff
            }.catch {
                logger.logException(it)
                // Do stuff
            }.collect()
        }
    }

    fun removeFromFavourites(media: Media) {
        viewModelScope.launch(dispatcher) {
            removeFromFavouritesUseCase(media).onStart {
                // Do stuff
            }.onCompletion {
                // Do stuff
            }.catch {
                logger.logException(it)
                // Do stuff
            }.collect()
        }
    }
}
