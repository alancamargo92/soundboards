package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.data.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.model.*
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

class MediaRepositoryImpl(
        crashReportManager: CrashReportManager,
        private val localDataSource: MediaLocalDataSource
) : MediaRepository(crashReportManager) {

    override suspend fun getMedia(mediaType: MediaType): Result<List<Media>> {
        return try {
            val media = localDataSource.getMediaList().sort()
            Success(media)
        } catch (t: Throwable) {
            crashReportManager.logException(t)
            GenericError
        }
    }

    override suspend fun getFavourites(): Result<List<Media>> = GenericError

    override suspend fun saveToFavourites(media: Media) { }

    override suspend fun removeFromFavourites(media: Media) { }

    override suspend fun isSavedToFavourites(media: Media): Result<Boolean> = GenericError

}