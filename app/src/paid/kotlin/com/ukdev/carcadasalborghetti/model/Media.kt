package com.ukdev.carcadasalborghetti.model

import com.google.gson.annotations.SerializedName

data class Media(
        val id: String,
        @SerializedName("name") val title: String
) {

    var position: Int = 0

    override fun toString() = title

    companion object {
        private const val FILE_NAME_SEPARATOR = '#'

        fun composeFileName(media: Media): String {
            return "${media.id}$FILE_NAME_SEPARATOR${media.position}$FILE_NAME_SEPARATOR${media.title}"
        }

        fun fromFileName(fileName: String): Media {
            val parts = fileName.split(FILE_NAME_SEPARATOR)
            val id = parts[0]
            val position = parts[1].toInt()
            val title = parts[2]
            return Media(id, title).also {
                it.position = position
            }
        }
    }

}