package com.ukdev.carcadasalborghetti.ui.viewmodel.medialist

import androidx.annotation.StringRes
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiOperation

sealed class MediaListUiAction {

    data class PlayAudio(val media: UiMedia) : MediaListUiAction()

    data class PlayVideo(val media: UiMedia) : MediaListUiAction()

    object StopAudioPlayback : MediaListUiAction()

    data class ShareMedia(
        @StringRes val chooserTitleRes: Int,
        @StringRes val chooserSubjectRes: Int,
        val chooserType: String,
        val media: UiMedia
    ) : MediaListUiAction()

    data class ShowAvailableOperations(
        val operations: List<UiOperation>,
        val media: UiMedia
    ) : MediaListUiAction()
}
