package com.ukdev.carcadasalborghetti.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ukdev.carcadasalborghetti.model.Audio
import com.ukdev.carcadasalborghetti.repository.AudioRepository

class AudioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AudioRepository()

    fun getAudios(): LiveData<List<Audio>> = MutableLiveData<List<Audio>>().apply {
        postValue(repository.getMedia())
    }

}