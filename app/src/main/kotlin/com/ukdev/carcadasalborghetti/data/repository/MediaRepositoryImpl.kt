package com.ukdev.carcadasalborghetti.data.repository

import com.ukdev.carcadasalborghetti.core.tools.Logger
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val remoteDataSource: MediaRemoteDataSource,
    private val localDataSource: MediaLocalDataSource,
    private val logger: Logger
) : MediaRepository {

    override suspend fun getMediaList(mediaType: MediaType): List<Media> {
        return runCatching {
            logger.debug("Fetching from remote...")
            remoteDataSource.getMediaList(mediaType)
        }.getOrElse {
            logger.error(it)
            logger.debug("Fetching from local...")
            localDataSource.getMediaList(mediaType)
        }
    }

    override fun getFavourites(): Flow<List<Media>> {
        return localDataSource.getFavourites()
    }

    override suspend fun saveToFavourites(media: Media) {
        localDataSource.saveToFavourites(media)
    }

    override suspend fun removeFromFavourites(media: Media) {
        localDataSource.removeFromFavourites(media)
    }

    override suspend fun isSavedToFavourites(media: Media): Boolean {
        return localDataSource.isSavedToFavourites(media)
    }

    override suspend fun prepareMedia(media: Media): Media {
        return runCatching {
            val destinationFile = localDataSource.createFile(media)
            val downloadedFile = remoteDataSource.download(media, destinationFile)
            val fileUri = localDataSource.getFileUri(downloadedFile)
            media.copy(id = fileUri)
        }.getOrElse {
            val destinationFile = localDataSource.createFile(media)
            val fileUri = localDataSource.getFileUri(destinationFile)
            media.copy(id = fileUri)
        }
    }
}
