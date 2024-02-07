package com.ukdev.carcadasalborghetti.ui.mapping

import com.ukdev.carcadasalborghetti.domain.model.Operation
import com.ukdev.carcadasalborghetti.ui.model.UiOperation

fun Operation.toUi() = when (this) {
    Operation.ADD_TO_FAVOURITES -> UiOperation.ADD_TO_FAVOURITES
    Operation.REMOVE_FROM_FAVOURITES -> UiOperation.REMOVE_FROM_FAVOURITES
    Operation.SHARE -> UiOperation.SHARE
}
