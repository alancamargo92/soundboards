package com.ukdev.carcadasalborghetti.data

import android.content.Context
import android.net.Uri
import com.ukdev.carcadasalborghetti.R

class MediaLocalDataSourceImpl(private val context: Context) : MediaLocalDataSource {

    override fun getTitles(): Array<String> {
        return context.resources.getStringArray(R.array.titles)
    }

    override suspend fun getAudioUris(): Array<Uri> {
        val typedArray = context.resources.obtainTypedArray(R.array.audios)
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