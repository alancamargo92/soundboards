package com.ukdev.carcadasalborghetti.domain.usecase

import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.Operation

interface GetAvailableOperationsUseCase {

    operator fun invoke(media: Media): List<Operation>
}
