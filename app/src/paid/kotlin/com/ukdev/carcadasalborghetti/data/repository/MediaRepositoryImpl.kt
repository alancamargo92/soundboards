package com.ukdev.carcadasalborghetti.data.repository

import androidx.lifecycle.LiveData
import com.ukdev.carcadasalborghetti.data.entities.Result
import com.ukdev.carcadasalborghetti.data.entities.Success
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.domain.entities.Operation
import com.ukdev.carcadasalborghetti.framework.local.db.FavouritesDao
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.IOHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    logger: Logger,
    private val remoteDataSource: MediaRemoteDataSource,
    private val localDataSource: MediaLocalDataSource,
    private val favouritesDao: FavouritesDao,
    private val ioHelper: IOHelper
) : MediaRepository(logger) {

    override suspend fun getMedia(mediaType: MediaType): Result<List<Media>> {
        return ioHelper.safeIOCall(mainCall = {
            remoteDataSource.listMedia(mediaType).sort()
        }, alternative = {
            localDataSource.listMedia(mediaType)
        })
    }

    override suspend fun getFavourites(): Result<LiveData<List<Media>>> {
        return ioHelper.safeIOCall {
            favouritesDao.getFavourites()
        }
    }

    override fun saveToFavourites(media: Media): Flow<Unit> = flow {
        val task = favouritesDao.insert(media)
        emit(task)
    }

    override fun removeFromFavourites(media: Media): Flow<Unit> = flow {
        val task = favouritesDao.delete(media)
        emit(task)
    }

    override suspend fun getAvailableOperations(media: Media): List<Operation> {
        val operations = arrayListOf(Operation.SHARE)

        if (media.type != MediaType.BOTH) {
            val result = withContext(Dispatchers.IO) {
                isSavedToFavourites(media)
            }

            if (result is Success) {
                val isSavedToFavourites = result.body

                val operation = if (isSavedToFavourites)
                    Operation.REMOVE_FROM_FAVOURITES
                else
                    Operation.ADD_TO_FAVOURITES

                operations.add(operation)
            }
        } else {
            operations.add(Operation.REMOVE_FROM_FAVOURITES)
        }

        return operations
    }

    private suspend fun isSavedToFavourites(media: Media): Result<Boolean> {
        return ioHelper.safeIOCall {
            favouritesDao.count(media.id) > 0
        }
    }
}
