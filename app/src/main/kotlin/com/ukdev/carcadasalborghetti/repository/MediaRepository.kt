package com.ukdev.carcadasalborghetti.repository

import android.content.Context
import com.ukdev.carcadasalborghetti.model.ErrorType
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class MediaRepository : KoinComponent {

    protected val context: Context by inject()

    abstract fun getMedia(mediaType: MediaType, resultCallback: ResultCallback)

    interface ResultCallback {
        fun onMediaFound(media: List<Media>)
        fun onError(errorType: ErrorType)
    }

}