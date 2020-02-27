package com.ukdev.carcadasalborghetti.model

import android.net.Uri

data class Media(val title: String, val uri: Uri) {

    var position: Int = 0

    companion object {
        private const val FILE_NAME_SEPARATOR = '#'

        fun composeFileName(media: Media): String {
            return "${media.position}$FILE_NAME_SEPARATOR${media.title}.mp3"
        }

        fun fromFileName(fileName: String): Media {
            val parts = fileName.split(FILE_NAME_SEPARATOR)
            val position = parts[0].toInt()
            val title = parts[1]
            return Media(title, Uri.EMPTY).also {
                it.position = position
            }
        }
    }

}