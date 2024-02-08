package com.ukdev.carcadasalborghetti.data.repository

import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSourceV2
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSourceV2
import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import com.ukdev.carcadasalborghetti.domain.repository.MediaRepositoryV2
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryV2Impl @Inject constructor(
    private val remoteDataSource: MediaRemoteDataSourceV2,
    private val localDataSource: MediaLocalDataSourceV2,
    private val logger: Logger
) : MediaRepositoryV2 {

    override suspend fun getMediaList(mediaType: MediaTypeV2): List<MediaV2> {
        return runCatching {
            logger.debug("Fetching from remote...")
            remoteDataSource.getMediaList(mediaType)
        }.getOrElse {
            logger.error(it)
            logger.debug("Fetching from local...")
            localDataSource.getMediaList(mediaType)
        }
    }

    override fun getFavourites(): Flow<List<MediaV2>> {
        return localDataSource.getFavourites()
    }

    override suspend fun saveToFavourites(media: MediaV2) {
        localDataSource.saveToFavourites(media)
    }

    override suspend fun removeFromFavourites(media: MediaV2) {
        localDataSource.removeFromFavourites(media)
    }

    override suspend fun isSavedToFavourites(media: MediaV2): Boolean {
        return localDataSource.isSavedToFavourites(media)
    }

    override suspend fun downloadMedia(media: MediaV2): MediaV2 {
        return runCatching {
            val destinationFile = localDataSource.createFile(media)
            val downloadedFile = remoteDataSource.download(media, destinationFile)
            val fileUri = localDataSource.getFileUri(downloadedFile)
            media.copy(id = fileUri)
        }.getOrElse {
            media
        }
    }
}
