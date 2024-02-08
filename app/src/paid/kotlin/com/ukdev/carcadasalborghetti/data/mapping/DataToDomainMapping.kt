package com.ukdev.carcadasalborghetti.data.mapping

import com.ukdev.carcadasalborghetti.data.model.DbMedia
import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2

fun DbMedia.toDomain() = MediaV2(
    id = id,
    title = title,
    type = MediaTypeV2.valueOf(typeString)
)
