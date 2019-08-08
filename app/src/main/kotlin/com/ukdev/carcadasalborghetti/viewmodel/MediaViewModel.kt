package com.ukdev.carcadasalborghetti.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ukdev.carcadasalborghetti.model.ErrorType
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.repository.MediaRepository
import com.ukdev.carcadasalborghetti.repository.MediaRepositoryImpl
import com.ukdev.carcadasalborghetti.view.ViewLayer

class MediaViewModel(
        application: Application
) : AndroidViewModel(application), MediaRepository.ResultCallback {

    private val repository: MediaRepository = MediaRepositoryImpl()

    private lateinit var view: ViewLayer

    fun getMedia(mediaType: MediaType, view: ViewLayer) {
        this.view = view
        repository.getMedia(mediaType, resultCallback = this@MediaViewModel)
    }

    override fun onMediaFound(media: List<Media>) {
        val liveData = MutableLiveData<List<Media>>().apply {
            val sortedData = sort(media)
            value = sortedData
        }
        view.displayMedia(liveData)
    }

    override fun onError(errorType: ErrorType) {
        view.onErrorFetchingData(errorType)
    }

    private fun sort(rawList: List<Media>): List<Media> {
        return rawList.sortedBy { it.title.split(".").last().trim() }
                .apply {
                    forEachIndexed { index, audio ->
                        audio.position = index + 1
                    }
                }
    }

}