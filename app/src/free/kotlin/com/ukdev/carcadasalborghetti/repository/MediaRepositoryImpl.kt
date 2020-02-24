package com.ukdev.carcadasalborghetti.repository

import com.ukdev.carcadasalborghetti.data.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.model.*
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepositoryImpl(
        crashReportManager: CrashReportManager,
        private val localDataSource: MediaLocalDataSource
) : MediaRepository(crashReportManager) {

    override suspend fun getMedia(mediaType: MediaType): Result<List<Media>> {
        return try {
            val titles = localDataSource.getTitles()

            val audioUris = withContext(Dispatchers.IO) {
                localDataSource.getAudioUris()
            }

            val media = arrayListOf<Media>().apply {
                titles.forEachIndexed { index, title ->
                    add(Media(title, audioUris[index]))
                }
            }.sort()

            Success(media)
        } catch (t: Throwable) {
            crashReportManager.logException(t)
            GenericError
        }
    }

}