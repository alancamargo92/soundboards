package com.ukdev.carcadasalborghetti.domain.repository

import androidx.lifecycle.LiveData
import com.ukdev.carcadasalborghetti.data.model.Result
import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.Operation
import kotlinx.coroutines.flow.Flow

abstract class MediaRepository(protected val logger: Logger) {

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
