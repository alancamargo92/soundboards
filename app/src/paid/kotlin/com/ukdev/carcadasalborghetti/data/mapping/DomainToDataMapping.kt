package com.ukdev.carcadasalborghetti.data.mapping

import com.ukdev.carcadasalborghetti.data.model.DbMedia
import com.ukdev.carcadasalborghetti.domain.model.Media

fun Media.toDb() = DbMedia(
    id = id,
    title = title,
    typeString = type.name
)
