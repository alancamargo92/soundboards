package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.Operation
import kotlinx.coroutines.flow.Flow

interface GetAvailableOperationsUseCase {

    operator fun invoke(media: Media): Flow<List<Operation>>
}
