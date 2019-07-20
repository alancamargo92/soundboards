package com.ukdev.carcadasalborghetti.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ukdev.carcadasalborghetti.model.Video
import com.ukdev.carcadasalborghetti.repository.VideoRepository

class VideoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VideoRepository()

    fun getVideos(): LiveData<List<Video>> = MutableLiveData<List<Video>>().apply {
        postValue(repository.getVideos())
    }

}