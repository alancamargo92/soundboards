package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.model.Video

class VideoRepository : MediaRepository<Video>() {

    override fun getMedia(resultCallback: ResultCallback<Video>) { }

}