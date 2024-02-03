package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import kotlinx.coroutines.flow.Flow

interface RemoveFromFavouritesUseCase {

    operator fun invoke(media: MediaV2): Flow<Unit>
}
