package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.api.DIR_AUDIO
import com.ukdev.carcadasalborghetti.api.DIR_VIDEO
import com.ukdev.carcadasalborghetti.api.requests.MediaRequest
import com.ukdev.carcadasalborghetti.api.tools.ApiProvider
import com.ukdev.carcadasalborghetti.model.*
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MediaRepositoryImpl(
        crashReportManager: CrashReportManager,
        private val apiProvider: ApiProvider
) : MediaRepository(crashReportManager) {

    override suspend fun getMedia(mediaType: MediaType): Result<List<Media>> {
        val dir = if (mediaType == MediaType.AUDIO)
            DIR_AUDIO
        else
            DIR_VIDEO
        val request = MediaRequest(dir)
        val api = apiProvider.getDropboxService()

        return try {
            val media = withContext(Dispatchers.IO) {
                api.listMedia(request).entries.sort()
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