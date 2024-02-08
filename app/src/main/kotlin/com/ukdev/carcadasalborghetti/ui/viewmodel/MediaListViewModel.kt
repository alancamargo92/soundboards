package com.ukdev.carcadasalborghetti.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.di.IoDispatcher
import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.Operation
import com.ukdev.carcadasalborghetti.domain.usecase.GetAvailableOperationsUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetMediaListUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.RemoveFromFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.SaveToFavouritesUseCase
import com.ukdev.carcadasalborghetti.ui.mapping.toDomain
import com.ukdev.carcadasalborghetti.ui.mapping.toUi
import com.ukdev.carcadasalborghetti.ui.model.UiError
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiMediaType
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
    private val getMediaListUseCase: GetMediaListUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val saveToFavouritesUseCase: SaveToFavouritesUseCase,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    private val getAvailableOperationsUseCase: GetAvailableOperationsUseCase,
    private val logger: Logger,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(MediaListUiState())
    private val _action = MutableSharedFlow<MediaListUiAction>()

    val state = _state.asStateFlow()
    val action = _action.asSharedFlow()

    fun getMediaList(mediaType: MediaTypeV2, isRefreshing: Boolean) {
        viewModelScope.launch(dispatcher) {
            getMediaListUseCase(mediaType).onStart {
                _state.update {
                    if (isRefreshing) {
                        it.onRefreshing()
                    } else {
                        it.onLoading()
                    }
                }
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
                    val uiMediaList = mediaList.map { it.toUi() }
                    _state.update { it.onMediaListReceived(uiMediaList) }
                }
            }
        }
    }

    fun getFavourites(isRefreshing: Boolean) {
        viewModelScope.launch(dispatcher) {
            getFavouritesUseCase().onStart {
                _state.update {
                    if (isRefreshing) {
                        it.onRefreshing()
                    } else {
                        it.onLoading()
                    }
                }
            }.onCompletion {
                _state.update { it.onFinishedLoading() }
            }.catch { exception ->
                logger.error(exception)
                _state.update { it.onError(UiError.UNKNOWN) }
            }.collect { mediaList ->
                if (mediaList.isEmpty()) {
                    _state.update { it.onError(UiError.NO_FAVOURITES) }
                } else {
                    val uiMediaList = mediaList.map { it.toUi() }
                    _state.update { it.onMediaListReceived(uiMediaList) }
                }
            }
        }
    }

    fun onItemClicked(media: UiMedia) {
        val action = MediaListUiAction.PlayMedia(media)
        sendAction(action)
    }

    fun onItemLongClicked(media: UiMedia) {
        viewModelScope.launch(dispatcher) {
            val domainMedia = media.toDomain()
            getAvailableOperationsUseCase(domainMedia).collect { operations ->
                val action = if (operations.isOnlyShare()) {
                    buildShareMediaAction(media)
                } else {
                    val uiOperations = operations.map { it.toUi() }
                    MediaListUiAction.ShowAvailableOperations(uiOperations, media)
                }

                _action.emit(action)
            }
        }
    }

    fun onStopButtonClicked() {
        sendAction(MediaListUiAction.StopPlayback)
    }

    fun onOperationSelected(operation: UiOperation, media: UiMedia) {
        when (operation) {
            UiOperation.ADD_TO_FAVOURITES -> saveToFavourites(media)
            UiOperation.REMOVE_FROM_FAVOURITES -> removeFromFavourites(media)
            UiOperation.SHARE -> {
                val action = buildShareMediaAction(media)
                sendAction(action)
            }
        }
    }

    private fun saveToFavourites(media: UiMedia) {
        viewModelScope.launch(dispatcher) {
            val domainMedia = media.toDomain()
            saveToFavouritesUseCase(domainMedia).catch {
                logger.error(it)
            }.collect()
        }
    }

    private fun removeFromFavourites(media: UiMedia) {
        viewModelScope.launch(dispatcher) {
            val domainMedia = media.toDomain()
            removeFromFavouritesUseCase(domainMedia).catch {
                logger.error(it)
            }.collect()
        }
    }

    private fun buildShareMediaAction(media: UiMedia): MediaListUiAction.ShareMedia {
        val chooserType = when (media.type) {
            UiMediaType.AUDIO -> "audio/*"
            UiMediaType.VIDEO -> "video/*"
        }

        return MediaListUiAction.ShareMedia(
            chooserTitleRes = R.string.chooser_title_share,
            chooserSubjectRes = R.string.chooser_subject_share,
            chooserType = chooserType,
            media = media
        )
    }

    private fun sendAction(action: MediaListUiAction) = viewModelScope.launch(dispatcher) {
        _action.emit(action)
    }

    private fun List<Operation>.isOnlyShare(): Boolean {
        return size == 1 && first() == Operation.SHARE
    }
}
