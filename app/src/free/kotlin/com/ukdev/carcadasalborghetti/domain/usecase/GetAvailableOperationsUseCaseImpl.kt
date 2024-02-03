package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.domain.model.Operation
import javax.inject.Inject

class GetAvailableOperationsUseCaseImpl @Inject constructor() : GetAvailableOperationsUseCase {

    override fun invoke(media: MediaV2) = listOf(Operation.SHARE)
}
