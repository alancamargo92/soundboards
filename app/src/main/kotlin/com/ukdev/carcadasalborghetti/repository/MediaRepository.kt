package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType

abstract class MediaRepository {

    abstract suspend fun getMedia(mediaType: MediaType): List<Media>

    protected fun List<Media>.sort(): List<Media> {
        return this.sortedBy { it.title.split(".").last().trim() }.apply {
            forEachIndexed { index, audio ->
                audio.position = index + 1
            }
        }
    }

}