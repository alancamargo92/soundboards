package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.BuildConfig
import com.ukdev.carcadasalborghetti.api.DropboxApi
import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.model.*
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import com.ukdev.carcadasalborghetti.utils.getService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MediaRepositoryImpl(
        crashReportManager: CrashReportManager
) : MediaRepository(crashReportManager) {

    private val api by lazy { getService(DropboxApi::class, BuildConfig.BASE_URL) }

    override suspend fun getMedia(mediaType: MediaType): Result<List<Media>> {
        return withContext(Dispatchers.IO) {
            val dir = if (mediaType == MediaType.AUDIO)
                DropboxApi.DIR_AUDIO
            else
                DropboxApi.DIR_VIDEO
            val request = MediaRequest(dir)

            try {
                val media = api.listMedia(request).entries.sort()
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

}