package com.ukdev.carcadasalborghetti.repository

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.*
import com.ukdev.carcadasalborghetti.utils.CrashReportManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepositoryImpl(
        crashReportManager: CrashReportManager,
        private val context: Context
) : MediaRepository(crashReportManager) {

    override suspend fun getMedia(mediaType: MediaType): Result<List<Media>> {
        return withContext(Dispatchers.IO) {
            try {
                val res = context.resources
                val titles = res.getStringArray(R.array.titles)
                val audioUris = getAudioUris(res)

                val media = arrayListOf<Media>().apply {
                    titles.forEachIndexed { index, title ->
                        add(Media(title, index + 1, audioUris[index]))
                    }
                }.sort()

                Success(media)
            } catch (t: Throwable) {
                crashReportManager.logException(t)
                GenericError
            }
        }
    }

    private fun getAudioUris(resources: Resources): Array<Uri> {
        val typedArray = resources.obtainTypedArray(R.array.audios)
        val audios = IntArray(typedArray.length()).also {
            it.forEachIndexed { index, _ ->
                it[index] = typedArray.getResourceId(index, 0)
            }
        }
        typedArray.recycle()

        return audios.map { resId ->
            Uri.parse("android.resource://${context.packageName}/$resId")
        }.toTypedArray()
    }

}