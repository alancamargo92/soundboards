package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.domain.model.Operation
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepositoryV2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAvailableOperationsUseCaseImpl @Inject constructor(
    private val repository: MediaRepositoryV2
) : GetAvailableOperationsUseCase {

    override fun invoke(media: MediaV2): Flow<List<Operation>> = flow {
        val operations = mutableListOf(Operation.SHARE)

        val operationToAdd = if (repository.isSavedToFavourites(media)) {
            Operation.REMOVE_FROM_FAVOURITES
        } else {
            Operation.ADD_TO_FAVOURITES
        }

        operations.add(operationToAdd)
        emit(operations)
    }
}
