package com.ukdev.carcadasalborghetti.repository

import android.content.res.Resources
import android.net.Uri
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Audio

class AudioRepository : MediaRepository<Audio>() {

    override fun getMedia(): List<Audio> {
        with(context.resources) {
            val titles = getStringArray(R.array.titles)
            val lengths = getStringArray(R.array.lengths)
            val audioUris = getAudioUris(this)

            val rawList = arrayListOf<Audio>().apply {
                titles.forEachIndexed { index, title ->
                    add(Audio(title, lengths[index], index + 1, audioUris[index]))
                }
            }

            return rawList.sortedBy { it.title.split(".").last().trim() }
                    .apply {
                        forEachIndexed { index, audio ->
                            audio.position = index + 1
                        }
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