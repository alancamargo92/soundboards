package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.domain.model.Operation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetAvailableOperationsUseCaseImpl @Inject constructor() : GetAvailableOperationsUseCase {

    override fun invoke(media: MediaV2): Flow<List<Operation>> {
        val operations = listOf(Operation.SHARE)
        return flowOf(operations)
    }
}
