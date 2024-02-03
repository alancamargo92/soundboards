package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepositoryV2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveFromFavouritesUseCaseImpl @Inject constructor(
    private val repository: MediaRepositoryV2
) : RemoveFromFavouritesUseCase {

    override fun invoke(media: MediaV2): Flow<Unit> = flow {
        repository.removeFromFavourites(media)
        emit(Unit)
    }
}
