package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.api.tools.IOHelper
import com.ukdev.carcadasalborghetti.data.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import com.ukdev.carcadasalborghetti.model.Result
import com.ukdev.carcadasalborghetti.utils.CrashReportManager

class MediaRepositoryImpl(
        crashReportManager: CrashReportManager,
        private val remoteDataSource: MediaRemoteDataSource,
        private val localDataSource: MediaLocalDataSource,
        private val ioHelper: IOHelper
) : MediaRepository(crashReportManager) {

    override suspend fun getMedia(mediaType: MediaType): Result<List<Media>> {
        return ioHelper.safeIOCall(mainCall = {
            remoteDataSource.listMedia(mediaType).sort()
        }, alternative = {
            localDataSource.listMedia(mediaType)
        })
    }

}