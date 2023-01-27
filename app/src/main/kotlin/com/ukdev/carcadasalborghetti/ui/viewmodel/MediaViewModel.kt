package com.ukdev.carcadasalborghetti.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukdev.carcadasalborghetti.data.entities.Result
import com.ukdev.carcadasalborghetti.data.repository.MediaRepository
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.domain.entities.Operation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MediaViewModel(
    private val repository: MediaRepository,
    private val crashReportManager: CrashReportManager
) : ViewModel() {

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
            repository.saveToFavourites(media).flowOn(Dispatchers.IO).catch {
                crashReportManager.logException(it)
            }.collect()
        }
    }

    fun removeFromFavourites(media: Media) {
        viewModelScope.launch {
            repository.removeFromFavourites(media).flowOn(Dispatchers.IO).catch {
                crashReportManager.logException(it)
            }.collect()
        }
    }

}