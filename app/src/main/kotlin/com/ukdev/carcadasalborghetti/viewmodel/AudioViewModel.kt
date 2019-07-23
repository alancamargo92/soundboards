package com.ukdev.carcadasalborghetti.viewmodel

import android.app.Application
import com.ukdev.carcadasalborghetti.model.Audio
import com.ukdev.carcadasalborghetti.repository.AudioRepository
import com.ukdev.carcadasalborghetti.repository.MediaRepository

class AudioViewModel(application: Application) : MediaViewModel<Audio>(application) {

    override val repository: MediaRepository<Audio> = AudioRepository()

}