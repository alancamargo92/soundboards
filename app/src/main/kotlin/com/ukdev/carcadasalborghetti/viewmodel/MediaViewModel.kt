package com.ukdev.carcadasalborghetti.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.repository.MediaRepository

abstract class MediaViewModel<T: Media>(application: Application) : AndroidViewModel(application) {

    protected abstract val repository: MediaRepository<T>

    fun getMedia(): LiveData<List<T>> = MutableLiveData<List<T>>().apply {
        postValue(repository.getMedia())
    }

}