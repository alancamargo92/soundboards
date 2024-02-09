package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface GetFavouritesUseCase {

    operator fun invoke(): Flow<List<Media>>
}
