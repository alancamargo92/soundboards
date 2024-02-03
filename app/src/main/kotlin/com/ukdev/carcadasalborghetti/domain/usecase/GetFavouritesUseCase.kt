package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import kotlinx.coroutines.flow.Flow

interface GetFavouritesUseCase {

    operator fun invoke(): Flow<List<MediaV2>>
}
