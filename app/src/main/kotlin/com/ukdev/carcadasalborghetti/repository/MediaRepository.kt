package com.ukdev.carcadasalborghetti.repository

import androidx.lifecycle.LiveData
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.model.Operation
import com.ukdev.carcadasalborghetti.model.Result
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

abstract class MediaRepository(protected val crashReportManager: CrashReportManager) {

    abstract suspend fun getMedia(mediaType: MediaType): Result<List<Media>>
    abstract suspend fun getFavourites(): Result<LiveData<List<Media>>>
    abstract suspend fun saveToFavourites(media: Media)
    abstract suspend fun removeFromFavourites(media: Media)
    abstract suspend fun getAvailableOperations(media: Media): List<Operation>

    protected fun List<Media>.sort(): List<Media> {
        return this.sortedBy { it.title.split(".").first().trim() }.apply {
            forEachIndexed { index, audio ->
                audio.position = index + 1
            }
        }
    }

}