package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import kotlinx.coroutines.flow.Flow

interface GetMediaListUseCase {

    operator fun invoke(mediaType: MediaType): Flow<List<Media>>
}
