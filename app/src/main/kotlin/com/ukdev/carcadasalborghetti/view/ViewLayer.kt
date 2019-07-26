package com.ukdev.carcadasalborghetti.view

import androidx.lifecycle.LiveData
import com.ukdev.carcadasalborghetti.model.Media

interface ViewLayer<T: Media> {
    fun displayMedia(media: LiveData<List<T>>)
    fun onError()
}