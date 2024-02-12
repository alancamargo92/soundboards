package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMediaListUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : GetMediaListUseCase {

    override fun invoke(mediaType: MediaType): Flow<List<Media>> = flow {
        val mediaList = repository.getMediaList(mediaType).sortedBy { it.title }
        emit(mediaList)
    }
}
