package com.ukdev.carcadasalborghetti.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.model.Result
import com.ukdev.carcadasalborghetti.repository.MediaRepository
import kotlinx.coroutines.launch

class MediaViewModel(private val repository: MediaRepository) : ViewModel() {

    private val mediaLiveData = MutableLiveData<Result<List<Media>>>()

    suspend fun getMedia(mediaType: MediaType): LiveData<Result<List<Media>>> {
        val media = if (mediaType == MediaType.BOTH)
            repository.getFavourites()
        else
            repository.getMedia(mediaType)

        return mediaLiveData.apply {
            postValue(media)
        }
    }

    fun saveToFavourites(media: Media) {
        viewModelScope.launch {
            repository.saveToFavourites(media)
        }
    }

    fun removeFromFavourites(media: Media) {
        viewModelScope.launch {
            repository.removeFromFavourites(media)
        }
    }

}