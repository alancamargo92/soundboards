package com.ukdev.carcadasalborghetti.ui.viewmodel.medialist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.tools.Logger
import com.ukdev.carcadasalborghetti.di.IoDispatcher
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.Operation
import com.ukdev.carcadasalborghetti.domain.usecase.DownloadMediaUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetAvailableOperationsUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetMediaListUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.RemoveFromFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.SaveToFavouritesUseCase
import com.ukdev.carcadasalborghetti.ui.mapping.toDomain
import com.ukdev.carcadasalborghetti.ui.mapping.toUi
import com.ukdev.carcadasalborghetti.ui.model.MediaListFragmentType
import com.ukdev.carcadasalborghetti.ui.model.UiError
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiMediaType
import com.ukdev.carcadasalborghetti.ui.model.UiOperation
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
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

class MediaListViewModel @AssistedInject constructor(
    @Assisted private val fragmentType: MediaListFragmentType,
    private val getMediaListUseCase: GetMediaListUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val saveToFavouritesUseCase: SaveToFavouritesUseCase,
    private val removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
    private val getAvailableOperationsUseCase: GetAvailableOperationsUseCase,
    private val downloadMediaUseCase: DownloadMediaUseCase,
    private val logger: Logger,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(MediaListUiState())
    private val _action = MutableSharedFlow<MediaListUiAction>()

    val state = _state.asStateFlow()
    val action = _action.asSharedFlow()

    private var unfilteredMediaList = emptyList<UiMedia>()

    fun getMediaList(isRefreshing: Boolean) {
        viewModelScope.launch(dispatcher) {
            val flow = when (fragmentType) {
                MediaListFragmentType.AUDIO -> getMediaListUseCase(MediaType.AUDIO)
                MediaListFragmentType.VIDEO -> getMediaListUseCase(MediaType.VIDEO)
                MediaListFragmentType.FAVOURITES -> getFavouritesUseCase()
            }

            flow.onStart {
                _state.update {
                    if (isRefreshing) {
                        it.onRefreshing()
                    } else {
                        it.onLoading()
                    }
                }
            }.catch { exception ->
                logger.error(exception)
                val error = if (exception is IOException) {
                    UiError.CONNECTION
                } else {
                    UiError.UNKNOWN
                }

                _state.update { it.onError(error) }
            }.onCompletion {
                delay(timeMillis = 50)
                _state.update { it.onFinishedLoading() }
            }.collect { mediaList ->
                if (mediaList.isEmpty()) {
                    val error = if (fragmentType == MediaListFragmentType.FAVOURITES) {
                        UiError.NO_FAVOURITES
                    } else {
                        UiError.UNKNOWN
                    }

                    _state.update { it.onError(error) }
                } else {
                    val uiMediaList = mediaList.map { it.toUi() }
                    unfilteredMediaList = uiMediaList
                    _state.update { it.onMediaListReceived(uiMediaList) }
                }

                if (fragmentType == MediaListFragmentType.FAVOURITES) {
                    _state.update { it.onFinishedLoading() }
                }
            }
        }
    }

    fun onItemClicked(media: UiMedia) {
        viewModelScope.launch(dispatcher) {
            downloadMedia(media) { downloadedMedia ->
                val action = when (downloadedMedia.type) {
                    UiMediaType.AUDIO -> {
                        _state.update { it.onMediaPlaying() }
                        MediaListUiAction.PlayAudio(downloadedMedia)
                    }
                    UiMediaType.VIDEO -> MediaListUiAction.PlayVideo(downloadedMedia)
                }

                _action.emit(action)
            }
        }
    }

    fun onItemLongClicked(media: UiMedia) {
        viewModelScope.launch(dispatcher) {
            val domainMedia = media.toDomain()
            getAvailableOperationsUseCase(domainMedia).collect { operations ->
                if (operations.isOnlyShare()) {
                    onOperationSelected(UiOperation.SHARE, media)
                } else {
                    val uiOperations = operations.map { it.toUi() }
                    val action = MediaListUiAction.ShowAvailableOperations(uiOperations, media)
                    _action.emit(action)
                }
            }
        }
    }

    fun onStopButtonClicked() {
        _state.update { it.onMediaFinishedPlaying() }
        sendAction(MediaListUiAction.StopAudioPlayback)
    }

    fun onPlaybackCompleted() {
        _state.update { it.onMediaFinishedPlaying() }
    }

    fun onOperationSelected(operation: UiOperation, media: UiMedia) {
        viewModelScope.launch(dispatcher) {
            when (operation) {
                UiOperation.ADD_TO_FAVOURITES -> saveToFavourites(media)
                UiOperation.REMOVE_FROM_FAVOURITES -> removeFromFavourites(media)
                UiOperation.SHARE -> shareMedia(media)
            }
        }
    }

    fun searchMedia(query: String?) {
        val mediaList = if (query.isNullOrBlank()) {
            unfilteredMediaList
        } else {
            unfilteredMediaList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }

        _state.update { it.onMediaListReceived(mediaList) }
    }

    private suspend fun saveToFavourites(media: UiMedia) {
        val domainMedia = media.toDomain()
        saveToFavouritesUseCase(domainMedia).catch {
            logger.error(it)
        }.collect()
    }

    private suspend fun removeFromFavourites(media: UiMedia) {
        val domainMedia = media.toDomain()
        removeFromFavouritesUseCase(domainMedia).catch {
            logger.error(it)
        }.collect()
    }

    private fun shareMedia(media: UiMedia) {
        viewModelScope.launch(dispatcher) {
            downloadMedia(media) { downloadedMedia ->
                val chooserType = when (downloadedMedia.type) {
                    UiMediaType.AUDIO -> "audio/*"
                    UiMediaType.VIDEO -> "video/*"
                }

                val action = MediaListUiAction.ShareMedia(
                    chooserTitleRes = R.string.chooser_title_share,
                    chooserSubjectRes = R.string.chooser_subject_share,
                    chooserType = chooserType,
                    media = downloadedMedia
                )
                sendAction(action)
            }
        }
    }

    private suspend fun downloadMedia(media: UiMedia, onSuccess: suspend (UiMedia) -> Unit) {
        val rawMedia = media.toDomain()
        downloadMediaUseCase(rawMedia).catch { exception ->
            logger.error(exception)
            _state.update { it.onError(UiError.UNKNOWN) }
        }.collect { downloadedMedia ->
            val uiMedia = downloadedMedia.toUi()
            onSuccess(uiMedia)
        }
    }

    private fun sendAction(action: MediaListUiAction) = viewModelScope.launch(dispatcher) {
        _action.emit(action)
    }

    private fun List<Operation>.isOnlyShare(): Boolean {
        return size == 1 && first() == Operation.SHARE
    }

    class Factory(
        private val assistedFactory: MediaListViewModelAssistedFactory,
        private val fragmentType: MediaListFragmentType
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(fragmentType) as? T ?: error("Invalid fragment")
        }
    }
}
