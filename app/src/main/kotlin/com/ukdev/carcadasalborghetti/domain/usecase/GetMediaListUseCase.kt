package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import kotlinx.coroutines.flow.Flow

interface GetMediaListUseCase {

    operator fun invoke(mediaType: MediaTypeV2): Flow<List<MediaV2>>
}
