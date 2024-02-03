package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepositoryV2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveToFavouritesUseCaseImpl @Inject constructor(
    private val repository: MediaRepositoryV2
) : SaveToFavouritesUseCase {

    override fun invoke(media: MediaV2): Flow<Unit> = flow {
        repository.saveToFavourites(media)
        emit(Unit)
    }
}
