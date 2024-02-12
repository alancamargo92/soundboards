package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface DownloadMediaUseCase {

    operator fun invoke(rawMedia: Media): Flow<Media>
}
