package com.ukdev.carcadasalborghetti.data.repository

import androidx.lifecycle.LiveData
import com.ukdev.carcadasalborghetti.data.entities.Result
import com.ukdev.carcadasalborghetti.data.entities.Success
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.data.tools.CrashReportManager
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.domain.entities.Operation
import com.ukdev.carcadasalborghetti.framework.local.db.FavouritesDatabase
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.IOHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepositoryImpl(
        crashReportManager: CrashReportManager,
        private val remoteDataSource: MediaRemoteDataSource,
        private val localDataSource: MediaLocalDataSource,
        private val favouritesDatabase: FavouritesDatabase,
        private val ioHelper: IOHelper
) : MediaRepository(crashReportManager) {

    override suspend fun getMedia(mediaType: MediaType): Result<List<Media>> {
        return ioHelper.safeIOCall(mainCall = {
            remoteDataSource.listMedia(mediaType).sort()
        }, alternative = {
            localDataSource.listMedia(mediaType)
        })
    }

    override suspend fun getFavourites(): Result<LiveData<List<Media>>> {
        return ioHelper.safeIOCall {
            favouritesDatabase.getFavourites()
        }
    }

    override suspend fun saveToFavourites(media: Media) {
        ioHelper.safeIOCall {
            favouritesDatabase.insert(media)
        }
    }

    override suspend fun removeFromFavourites(media: Media) {
        ioHelper.safeIOCall {
            favouritesDatabase.delete(media)
        }
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
            favouritesDatabase.count(media.id) > 0
        }
    }

}