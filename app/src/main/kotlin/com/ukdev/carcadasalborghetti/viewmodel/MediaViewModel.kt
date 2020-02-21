package com.ukdev.carcadasalborghetti.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.repository.MediaRepository

class MediaViewModel(private val repository: MediaRepository) : ViewModel() {

    private val mediaLiveData = MutableLiveData<List<Media>>()

    suspend fun getMedia(mediaType: MediaType): LiveData<List<Media>> {
        val media = repository.getMedia(mediaType)
        return mediaLiveData.apply {
            postValue(media)
        }
    }

}