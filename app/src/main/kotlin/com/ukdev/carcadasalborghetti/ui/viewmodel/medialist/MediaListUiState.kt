package com.ukdev.carcadasalborghetti.ui.viewmodel.medialist

import com.ukdev.carcadasalborghetti.ui.model.UiError
import com.ukdev.carcadasalborghetti.ui.model.UiMedia

data class MediaListUiState(
    val isLoading: Boolean = false,
    val showStopButton: Boolean = false,
    val error: UiError? = null,
    val mediaList: List<UiMedia>? = null,
    val isTryAgainButtonVisible: Boolean = false,
    val isRefreshing: Boolean = false
) {

    fun onLoading() = copy(
        isLoading = true,
        mediaList = null,
        error = null,
        isTryAgainButtonVisible = false
    )

    fun onFinishedLoading() = copy(isLoading = false, isRefreshing = false)

    fun onMediaPlaying() = copy(showStopButton = true)

    fun onMediaFinishedPlaying() = copy(showStopButton = false)

    fun onMediaListReceived(mediaList: List<UiMedia>) = copy(mediaList = mediaList)

    fun onError(error: UiError): MediaListUiState {
        val isTryAgainButtonVisible = error != UiError.NO_FAVOURITES
        return copy(
            error = error,
            mediaList = null,
            isTryAgainButtonVisible = isTryAgainButtonVisible
        )
    }

    fun onRefreshing() = onLoading().copy(
        isLoading = false,
        isRefreshing = true
    )
}
