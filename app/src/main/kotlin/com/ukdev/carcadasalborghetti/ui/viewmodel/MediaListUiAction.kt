package com.ukdev.carcadasalborghetti.ui.viewmodel

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.ui.model.UiOperation

sealed class MediaListUiAction {

    data class PlayMedia(val media: MediaV2) : MediaListUiAction()

    object StopPlayback : MediaListUiAction()

    data class ShareMedia(val media: MediaV2) : MediaListUiAction()

    data class ShowAvailableOperations(val operations: List<UiOperation>) : MediaListUiAction()
}
