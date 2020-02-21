package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.model.ErrorType
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType

interface MediaRepository {

    fun getMedia(mediaType: MediaType, resultCallback: ResultCallback)

    interface ResultCallback {
        fun onMediaFound(media: List<Media>)
        fun onError(errorType: ErrorType)
    }

}