package com.ukdev.carcadasalborghetti.data.mapping

import com.ukdev.carcadasalborghetti.data.model.DbMedia
import com.ukdev.carcadasalborghetti.domain.model.MediaV2

fun MediaV2.toDb() = DbMedia(
    id = id,
    title = title,
    typeString = type.name
)
