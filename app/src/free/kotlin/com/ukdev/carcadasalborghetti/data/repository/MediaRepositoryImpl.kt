package com.ukdev.carcadasalborghetti.data.repository

import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val localDataSource: MediaLocalDataSource
) : MediaRepository {

    override suspend fun getMedia(mediaType: MediaType): List<Media> {
        return localDataSource.getMediaList()
    }

    override fun getFavourites(): Flow<List<Media>> = flowOf()

    override suspend fun saveToFavourites(media: Media) = Unit

    override suspend fun removeFromFavourites(media: Media) = Unit

    override suspend fun isSavedToFavourites(media: Media) = false
}
