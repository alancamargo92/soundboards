package com.ukdev.carcadasalborghetti.ui.mapping

import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiMediaType

fun UiMedia.toDomain(): Media {
    val extension = when (type) {
        UiMediaType.AUDIO -> "mp3"
        UiMediaType.VIDEO -> "mp4"
    }

    return Media(
        id = uri.toString(),
        title = "$title.$extension",
        type = type.toDomain()
    )
}

private fun UiMediaType.toDomain() = when (this) {
    UiMediaType.AUDIO -> MediaType.AUDIO
    UiMediaType.VIDEO -> MediaType.VIDEO
}
