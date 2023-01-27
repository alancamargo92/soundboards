package com.ukdev.carcadasalborghetti.data.repository

import androidx.lifecycle.LiveData
import com.ukdev.carcadasalborghetti.data.entities.Result
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.domain.entities.Operation
import kotlinx.coroutines.flow.Flow

abstract class MediaRepository(protected val crashReportManager: CrashReportManager) {

    abstract suspend fun getMedia(mediaType: MediaType): Result<List<Media>>
    abstract suspend fun getFavourites(): Result<LiveData<List<Media>>>
    abstract fun saveToFavourites(media: Media): Flow<Unit>
    abstract fun removeFromFavourites(media: Media): Flow<Unit>
    abstract suspend fun getAvailableOperations(media: Media): List<Operation>

    protected fun List<Media>.sort(): List<Media> {
        return this.sortedBy { it.title.split(".").first().trim() }.apply {
            forEachIndexed { index, audio ->
                audio.position = index + 1
            }
        }
    }

}