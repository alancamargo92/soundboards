package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.repository.MediaRepositoryV2
import javax.inject.Inject

class GetFavouritesUseCaseImpl @Inject constructor(
    private val repository: MediaRepositoryV2
) : GetFavouritesUseCase {

    override fun invoke() = repository.getFavourites()
}