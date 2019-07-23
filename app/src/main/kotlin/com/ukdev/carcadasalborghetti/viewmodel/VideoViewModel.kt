package com.ukdev.carcadasalborghetti.viewmodel

import android.app.Application
import com.ukdev.carcadasalborghetti.model.Video
import com.ukdev.carcadasalborghetti.repository.MediaRepository
import com.ukdev.carcadasalborghetti.repository.VideoRepository

class VideoViewModel(application: Application) : MediaViewModel<Video>(application) {

    override val repository: MediaRepository<Video> = VideoRepository()

}