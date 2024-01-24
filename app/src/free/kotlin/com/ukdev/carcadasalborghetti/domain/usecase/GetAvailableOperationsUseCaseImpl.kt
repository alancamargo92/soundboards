package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.Operation
import javax.inject.Inject

class GetAvailableOperationsUseCaseImpl @Inject constructor() : GetAvailableOperationsUseCase {

    override fun invoke(media: Media) = listOf(Operation.SHARE)
}
