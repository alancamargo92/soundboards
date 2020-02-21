package com.ukdev.carcadasalborghetti.view

import com.ukdev.carcadasalborghetti.model.ErrorType

interface ViewLayer {
    fun onMediaError(errorType: ErrorType)
    fun notifyItemReady()
}