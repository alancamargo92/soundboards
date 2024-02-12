package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import javax.inject.Inject

class GetFavouritesUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : GetFavouritesUseCase {

    override fun invoke() = repository.getFavourites()
}