package com.ukdev.carcadasalborghetti.data.repository

import androidx.lifecycle.LiveData
import com.ukdev.carcadasalborghetti.data.entities.GenericError
import com.ukdev.carcadasalborghetti.data.entities.Result
import com.ukdev.carcadasalborghetti.data.entities.Success
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.domain.entities.Operation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    logger: Logger,
    private val localDataSource: MediaLocalDataSource
) : MediaRepository(logger) {

    override suspend fun getMedia(mediaType: MediaType): Result<List<Media>> {
        return try {
            val media = localDataSource.getMediaList().sort()
            Success(media)
        } catch (t: Throwable) {
            logger.logException(t)
            GenericError
        }
    }

    override suspend fun getFavourites(): Result<LiveData<List<Media>>> = GenericError

    override fun saveToFavourites(media: Media): Flow<Unit> = flow { }

    override fun removeFromFavourites(media: Media): Flow<Unit> = flow { }

    override suspend fun getAvailableOperations(media: Media): List<Operation> {
        return listOf(Operation.SHARE)
    }
}
