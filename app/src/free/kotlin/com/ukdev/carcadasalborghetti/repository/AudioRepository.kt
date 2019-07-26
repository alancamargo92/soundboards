package com.ukdev.carcadasalborghetti.repository

import android.content.res.Resources
import android.net.Uri
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Audio

class AudioRepository : MediaRepository<Audio>() {

    override fun getMedia(resultCallback: ResultCallback<Audio>) {
        val rawList = fetchData()
        val media = sort(rawList)
        resultCallback.onMediaFound(media)

    }

    private fun fetchData(): ArrayList<Audio> {
        val res = context.resources
        val titles = res.getStringArray(R.array.titles)
        val lengths = res.getStringArray(R.array.lengths)
        val audioUris = getAudioUris(res)

        return arrayListOf<Audio>().apply {
            titles.forEachIndexed { index, title ->
                add(Audio(title, lengths[index], index + 1, audioUris[index]))
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

    private fun sort(rawList: ArrayList<Audio>): List<Audio> {
        return rawList.sortedBy { it.title.split(".").last().trim() }
                .apply {
                    forEachIndexed { index, audio ->
                        audio.position = index + 1
                    }
                }
    }

}