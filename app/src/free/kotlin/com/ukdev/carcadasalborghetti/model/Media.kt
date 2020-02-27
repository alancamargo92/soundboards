package com.ukdev.carcadasalborghetti.model

import android.net.Uri

data class Media(val title: String, val uri: Uri) {

    var position: Int = 0

    companion object {
        private const val FILE_NAME_SEPARATOR = '#'

        fun composeFileName(media: Media): String {
            return "${media.uri}$FILE_NAME_SEPARATOR${media.position}$FILE_NAME_SEPARATOR${media.title}.mp3"
        }

        fun fromFileName(fileName: String): Media {
            val parts = fileName.split(FILE_NAME_SEPARATOR)
            val uri = Uri.parse(parts[0])
            val position = parts[1].toInt()
            val title = parts[2]
            return Media(title, uri).also {
                it.position = position
            }
        }
    }

}