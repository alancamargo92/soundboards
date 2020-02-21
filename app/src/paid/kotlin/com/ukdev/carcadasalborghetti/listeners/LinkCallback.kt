package com.ukdev.carcadasalborghetti.listeners

import com.ukdev.carcadasalborghetti.model.ErrorType

interface LinkCallback {
    fun onError(errorType: ErrorType)
}