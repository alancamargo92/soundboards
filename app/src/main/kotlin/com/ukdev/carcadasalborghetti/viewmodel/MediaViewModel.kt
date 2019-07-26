package com.ukdev.carcadasalborghetti.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.repository.MediaRepository
import com.ukdev.carcadasalborghetti.view.ViewLayer

abstract class MediaViewModel<T: Media>(
        application: Application
) : AndroidViewModel(application), MediaRepository.ResultCallback<T> {

    protected abstract val repository: MediaRepository<T>

    private lateinit var view: ViewLayer<T>

    fun getMedia(view: ViewLayer<T>) {
        this.view = view
        repository.getMedia(resultCallback = this@MediaViewModel)
    }

    override fun onMediaFound(media: List<T>) {
        val liveData = MutableLiveData<List<T>>().apply {
            postValue(media)
        }
        view.displayMedia(liveData)
    }

    override fun onError() {
        view.onError()
    }

}