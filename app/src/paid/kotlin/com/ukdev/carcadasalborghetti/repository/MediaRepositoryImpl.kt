package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.data.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.model.*
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MediaRepositoryImpl(
        crashReportManager: CrashReportManager,
        private val remoteDataSource: MediaRemoteDataSource
) : MediaRepository(crashReportManager) {

    override suspend fun getMedia(mediaType: MediaType): Result<List<Media>> {
        return try {
            val media = withContext(Dispatchers.IO) {
                remoteDataSource.listMedia(mediaType).sort()
            }
            Success(media)
        } catch (httpException: HttpException) {
            crashReportManager.logException(httpException)
            GenericError
        } catch (ioException: IOException) {
            crashReportManager.logException(ioException)
            NetworkError
        } catch (t: Throwable) {
            crashReportManager.logException(t)
            GenericError
        }
    }

}