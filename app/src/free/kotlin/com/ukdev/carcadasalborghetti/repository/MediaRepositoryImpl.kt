package com.ukdev.carcadasalborghetti.repository

import android.content.res.Resources
import android.net.Uri
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Media

class MediaRepositoryImpl : MediaRepository() {

    override fun getMedia(mediaType: Media.Type, resultCallback: ResultCallback) {
        val rawList = fetchData()
        val media = sort(rawList)
        resultCallback.onMediaFound(media)
    }

    private fun fetchData(): ArrayList<Media> {
        val res = context.resources
        val titles = res.getStringArray(R.array.titles)
        val lengths = res.getStringArray(R.array.lengths)
        val audioUris = getAudioUris(res)

        return arrayListOf<Media>().apply {
            titles.forEachIndexed { index, title ->
                add(Media(title, lengths[index], index + 1, audioUris[index]))
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

    private fun sort(rawList: ArrayList<Media>): List<Media> {
        return rawList.sortedBy { it.title.split(".").last().trim() }
                .apply {
                    forEachIndexed { index, audio ->
                        audio.position = index + 1
                    }
                }
    }

}