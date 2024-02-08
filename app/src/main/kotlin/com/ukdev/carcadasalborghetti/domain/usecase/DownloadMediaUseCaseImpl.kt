package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepositoryV2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DownloadMediaUseCaseImpl @Inject constructor(
    private val repository: MediaRepositoryV2
) : DownloadMediaUseCase {

    override fun invoke(rawMedia: MediaV2): Flow<MediaV2> = flow {
        val downloadedMedia = repository.downloadMedia(rawMedia)
        emit(downloadedMedia)
    }
}
