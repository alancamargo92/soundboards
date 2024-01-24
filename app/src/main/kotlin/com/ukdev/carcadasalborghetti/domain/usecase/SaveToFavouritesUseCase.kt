package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface SaveToFavouritesUseCase {

    operator fun invoke(media: Media): Flow<Unit>
}
