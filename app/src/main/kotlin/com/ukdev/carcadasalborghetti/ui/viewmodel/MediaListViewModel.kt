package com.ukdev.carcadasalborghetti.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.di.IoDispatcher
import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.domain.model.Operation
import com.ukdev.carcadasalborghetti.domain.usecase.GetAvailableOperationsUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetMediaListUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.RemoveFromFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.SaveToFavouritesUseCase
import com.ukdev.carcadasalborghetti.ui.mapping.toUi
import com.ukdev.carcadasalborghetti.ui.model.UiError
import com.ukdev.carcadasalborghetti.ui.model.UiOperation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MediaListViewModel @Inject constructor(
    private val useCases: UseCases,
    private val logger: Logger,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var selectedMedia: MediaV2? = null

    private val _state = MutableStateFlow(MediaListUiState())
    private val _action = MutableSharedFlow<MediaListUiAction>()

    val state = _state.asStateFlow()
    val action = _action.asSharedFlow()

    fun getMediaList(mediaType: MediaTypeV2) {
        viewModelScope.launch(dispatcher) {
            useCases.getMediaListUseCase(mediaType).onStart {
                _state.update { it.onLoading() }
            }.onCompletion {
                _state.update { it.onFinishedLoading() }
            }.catch { exception ->
                logger.error(exception)
                val error = if (exception is IOException) {
                    UiError.CONNECTION
                } else {
                    UiError.UNKNOWN
                }

                _state.update { it.onError(error) }
            }.collect { mediaList ->
                if (mediaList.isEmpty()) {
                    _state.update { it.onError(UiError.UNKNOWN) }
                } else {
                    _state.update { it.onMediaListReceived(mediaList) }
                }
            }
        }
    }

    fun getFavourites() {
        viewModelScope.launch(dispatcher) {
            useCases.getFavouritesUseCase().onStart {
                _state.update { it.onLoading() }
            }.onCompletion {
                _state.update { it.onFinishedLoading() }
            }.catch { exception ->
                logger.error(exception)
                _state.update { it.onError(UiError.UNKNOWN) }
            }.collect { mediaList ->
                if (mediaList.isEmpty()) {
                    _state.update { it.onError(UiError.NO_FAVOURITES) }
                } else {
                    _state.update { it.onMediaListReceived(mediaList) }
                }
            }
        }
    }

    fun getAvailableOperations(media: MediaV2): List<Operation> {
        return useCases.getAvailableOperationsUseCase(media)
    }

    fun onItemClicked(media: MediaV2) {
        val action = MediaListUiAction.PlayMedia(media)
        sendAction(action)
    }

    fun onStopButtonClicked() {
        sendAction(MediaListUiAction.StopPlayback)
    }

    fun onItemLongClicked(media: MediaV2) {
        selectedMedia = media
        val operations = useCases.getAvailableOperationsUseCase(media)
        val action = if (operations.isOnlyShare()) {
            MediaListUiAction.ShareMedia(media)
        } else {
            val uiOperations = operations.map { it.toUi() }
            MediaListUiAction.ShowAvailableOperations(uiOperations)
        }

        sendAction(action)
    }

    fun onOperationSelected(operation: UiOperation) {
        selectedMedia?.let { media ->
            when (operation) {
                UiOperation.ADD_TO_FAVOURITES -> saveToFavourites(media)
                UiOperation.REMOVE_FROM_FAVOURITES -> removeFromFavourites(media)
                UiOperation.SHARE -> shareMedia(media)
            }
        }
    }

    private fun saveToFavourites(media: MediaV2) {
        viewModelScope.launch(dispatcher) {
            useCases.saveToFavouritesUseCase(media).catch {
                logger.error(it)
            }.collect()
        }
    }

    private fun removeFromFavourites(media: MediaV2) {
        viewModelScope.launch(dispatcher) {
            useCases.removeFromFavouritesUseCase(media).catch {
                logger.error(it)
            }.collect()
        }
    }

    private fun shareMedia(media: MediaV2) {
        val action = MediaListUiAction.ShareMedia(media)
        sendAction(action)
    }

    private fun sendAction(action: MediaListUiAction) = viewModelScope.launch(dispatcher) {
        _action.emit(action)
    }

    private fun List<Operation>.isOnlyShare(): Boolean {
        return size == 1 && first() == Operation.SHARE
    }

    class UseCases @Inject constructor(
        val getMediaListUseCase: GetMediaListUseCase,
        val getFavouritesUseCase: GetFavouritesUseCase,
        val saveToFavouritesUseCase: SaveToFavouritesUseCase,
        val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
        val getAvailableOperationsUseCase: GetAvailableOperationsUseCase
    )
}
