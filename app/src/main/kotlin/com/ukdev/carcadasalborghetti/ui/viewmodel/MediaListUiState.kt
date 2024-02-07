package com.ukdev.carcadasalborghetti.ui.viewmodel

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.ui.model.UiError

data class MediaListUiState(
    val isLoading: Boolean = false,
    val showStopButton: Boolean = false,
    val error: UiError? = null,
    val mediaList: List<MediaV2>? = null
) {

    fun onLoading() = copy(isLoading = true, mediaList = null)

    fun onFinishedLoading() = copy(isLoading = false)

    fun onMediaPlaying() = copy(showStopButton = true)

    fun onMediaFinishedPlaying() = copy(showStopButton = false)

    fun onMediaListReceived(mediaList: List<MediaV2>) = copy(
        mediaList = mediaList,
        error = null
    )

    fun onError(error: UiError) = copy(error = error, mediaList = null)
}
