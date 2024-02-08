package com.ukdev.carcadasalborghetti.ui.mapping

import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import com.ukdev.carcadasalborghetti.ui.model.UiMediaType

fun UiMedia.toDomain(): MediaV2 {
    val extension = when (type) {
        UiMediaType.AUDIO -> "mp3"
        UiMediaType.VIDEO -> "mp4"
    }

    return MediaV2(
        id = uri.toString(),
        title = "$title.$extension",
        type = type.toDomain()
    )
}

private fun UiMediaType.toDomain() = when (this) {
    UiMediaType.AUDIO -> MediaTypeV2.AUDIO
    UiMediaType.VIDEO -> MediaTypeV2.VIDEO
}
