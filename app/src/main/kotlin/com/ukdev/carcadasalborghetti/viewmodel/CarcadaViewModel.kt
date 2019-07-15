package com.ukdev.carcadasalborghetti.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ukdev.carcadasalborghetti.model.Carcada
import com.ukdev.carcadasalborghetti.repository.CarcadaRepository
import com.ukdev.carcadasalborghetti.repository.CarcadaRepositoryImpl

class CarcadaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CarcadaRepository = CarcadaRepositoryImpl(application)

    fun getCarcadas(): LiveData<List<Carcada>> = MutableLiveData<List<Carcada>>().apply {
        postValue(repository.getCarcadas())
    }

}