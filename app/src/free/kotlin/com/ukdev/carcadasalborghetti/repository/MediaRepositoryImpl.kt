package com.ukdev.carcadasalborghetti.repository

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType

class MediaRepositoryImpl(private val context: Context) : MediaRepository {

    override fun getMedia(mediaType: MediaType, resultCallback: MediaRepository.ResultCallback) {
        val media = fetchData()
        resultCallback.onMediaFound(media)
    }

    private fun fetchData(): ArrayList<Media> {
        val res = context.resources
        val titles = res.getStringArray(R.array.titles)
        val audioUris = getAudioUris(res)

        return arrayListOf<Media>().apply {
            titles.forEachIndexed { index, title ->
                add(Media(title, index + 1, audioUris[index]))
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