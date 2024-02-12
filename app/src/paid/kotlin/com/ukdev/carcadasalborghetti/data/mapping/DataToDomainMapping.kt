package com.ukdev.carcadasalborghetti.data.mapping

import com.ukdev.carcadasalborghetti.data.model.DbMedia
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.Media

fun DbMedia.toDomain() = Media(
    id = id,
    title = title,
    type = MediaType.valueOf(typeString)
)
