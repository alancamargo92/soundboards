package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.domain.model.Operation
import kotlinx.coroutines.flow.Flow

interface GetAvailableOperationsUseCase {

    operator fun invoke(media: MediaV2): Flow<List<Operation>>
}
