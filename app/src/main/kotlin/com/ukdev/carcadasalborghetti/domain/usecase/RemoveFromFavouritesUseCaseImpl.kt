package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveFromFavouritesUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : RemoveFromFavouritesUseCase {

    override fun invoke(media: Media): Flow<Unit> = flow {
        repository.removeFromFavourites(media)
        emit(Unit)
    }
}
