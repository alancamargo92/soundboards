package com.ukdev.carcadasalborghetti.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.model.Operation
import com.ukdev.carcadasalborghetti.model.Result
import com.ukdev.carcadasalborghetti.repository.MediaRepository
import kotlinx.coroutines.launch

class MediaViewModel(private val repository: MediaRepository) : ViewModel() {

    private val audioLiveData = MutableLiveData<Result<List<Media>>>()
    private val videoLiveData = MutableLiveData<Result<List<Media>>>()

    suspend fun getMedia(mediaType: MediaType): LiveData<Result<List<Media>>> {
        val media = repository.getMedia(mediaType)

        val liveData = when (mediaType) {
            MediaType.AUDIO -> audioLiveData
            MediaType.VIDEO -> videoLiveData
            MediaType.BOTH -> throw IllegalArgumentException("Must be either audio or video")
        }

        return liveData.apply {
            postValue(media)
        }
    }

    suspend fun getFavourites(): Result<LiveData<List<Media>>> {
        return repository.getFavourites()
    }

    suspend fun getAvailableOperations(media: Media): List<Operation> {
        return repository.getAvailableOperations(media)
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