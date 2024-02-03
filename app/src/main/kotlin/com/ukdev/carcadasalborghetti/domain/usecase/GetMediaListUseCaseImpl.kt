package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepositoryV2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMediaListUseCaseImpl @Inject constructor(
    private val repository: MediaRepositoryV2
) : GetMediaListUseCase {

    override fun invoke(mediaType: MediaTypeV2): Flow<List<MediaV2>> = flow {
        val mediaList = repository.getMediaList(mediaType).sortedBy {
            it.title.split(".").first().trim()
        }

        emit(mediaList)
    }
}
