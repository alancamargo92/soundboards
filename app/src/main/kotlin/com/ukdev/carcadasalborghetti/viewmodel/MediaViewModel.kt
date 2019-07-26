package com.ukdev.carcadasalborghetti.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.repository.MediaRepository
import com.ukdev.carcadasalborghetti.repository.MediaRepositoryImpl
import com.ukdev.carcadasalborghetti.view.ViewLayer

class MediaViewModel(
        application: Application
) : AndroidViewModel(application), MediaRepository.ResultCallback {

    private val repository: MediaRepository = MediaRepositoryImpl()

    private lateinit var view: ViewLayer

    fun getMedia(mediaType: Media.Type, view: ViewLayer) {
        this.view = view
        repository.getMedia(mediaType, resultCallback = this@MediaViewModel)
    }

    override fun onMediaFound(media: List<Media>) {
        val liveData = MutableLiveData<List<Media>>().apply {
            postValue(media)
        }
        view.displayMedia(liveData)
    }

    override fun onError() {
        view.onError()
    }

}