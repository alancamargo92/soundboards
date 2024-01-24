package com.ukdev.carcadasalborghetti.framework.local

import android.content.Context
import android.net.Uri
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.domain.entities.Media
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MediaLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MediaLocalDataSource {

    override suspend fun getMediaList(): List<Media> {
        val titles = getTitles()
        val uris = getAudioUris()

        return arrayListOf<Media>().apply {
            titles.forEachIndexed { index, title ->
                add(Media(title, uris[index]))
            }
        }
    }

    private fun getTitles(): Array<String> {
        return context.resources.getStringArray(R.array.titles)
    }

    private suspend fun getAudioUris(): Array<Uri> = withContext(Dispatchers.IO) {
        val typedArray = context.resources.obtainTypedArray(R.array.audios)
        val audios = IntArray(typedArray.length()).also {
            it.forEachIndexed { index, _ ->
                it[index] = typedArray.getResourceId(index, 0)
            }
        }
        typedArray.recycle()

        audios.map { resId ->
            Uri.parse("android.resource://${context.packageName}/$resId")
        }.toTypedArray()
    }
}
