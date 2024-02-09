package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.Operation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetAvailableOperationsUseCaseImpl @Inject constructor() : GetAvailableOperationsUseCase {

    override fun invoke(media: Media): Flow<List<Operation>> {
        val operations = listOf(Operation.SHARE)
        return flowOf(operations)
    }
}
