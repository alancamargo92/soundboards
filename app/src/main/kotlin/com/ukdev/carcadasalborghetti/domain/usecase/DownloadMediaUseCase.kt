package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import kotlinx.coroutines.flow.Flow

interface DownloadMediaUseCase {

    operator fun invoke(rawMedia: MediaV2): Flow<MediaV2>
}
