package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DownloadMediaUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : DownloadMediaUseCase {

    override fun invoke(rawMedia: Media): Flow<Media> = flow {
        val downloadedMedia = repository.prepareMedia(rawMedia)
        emit(downloadedMedia)
    }
}
