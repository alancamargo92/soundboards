package com.ukdev.carcadasalborghetti.view

import com.ukdev.carcadasalborghetti.model.ErrorType

interface ViewLayer {
    fun onErrorFetchingData(errorType: ErrorType)
    fun onMediaError(errorType: ErrorType)
    fun notifyItemReady()
}