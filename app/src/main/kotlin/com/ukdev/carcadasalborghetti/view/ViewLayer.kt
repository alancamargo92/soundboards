package com.ukdev.carcadasalborghetti.view

import androidx.lifecycle.LiveData
import com.ukdev.carcadasalborghetti.model.ErrorType
import com.ukdev.carcadasalborghetti.model.Media

interface ViewLayer {
    fun displayMedia(media: LiveData<List<Media>>)
    fun onErrorFetchingData(errorType: ErrorType)
    fun onMediaError(errorType: ErrorType)
}