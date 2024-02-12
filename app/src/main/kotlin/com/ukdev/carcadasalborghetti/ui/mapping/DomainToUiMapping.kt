package com.ukdev.carcadasalborghetti.ui.mapping

import androidx.core.net.toUri
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.Operation
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiMediaType
import com.ukdev.carcadasalborghetti.ui.model.UiOperation

fun Operation.toUi() = when (this) {
    Operation.ADD_TO_FAVOURITES -> UiOperation.ADD_TO_FAVOURITES
    Operation.REMOVE_FROM_FAVOURITES -> UiOperation.REMOVE_FROM_FAVOURITES
    Operation.SHARE -> UiOperation.SHARE
}

fun Media.toUi() = UiMedia(
    uri = id.toUri(),
    title = title.replace("(.mp3|.mp4)".toRegex(), ""),
    type = type.toUi()
)

private fun MediaType.toUi() = when (this) {
    MediaType.AUDIO -> UiMediaType.AUDIO
    MediaType.VIDEO -> UiMediaType.VIDEO
}
