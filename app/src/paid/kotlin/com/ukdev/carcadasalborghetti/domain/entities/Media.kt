package com.ukdev.carcadasalborghetti.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity
@TypeConverters(Media.MediaTypeConverter::class)
data class Media(
        @PrimaryKey val id: String,
        val title: String,
        val type: MediaType,
        var position: Int = 0
) {

    override fun toString() = title

    companion object {
        private const val FILE_NAME_SEPARATOR = '#'

        fun composeFileName(media: Media): String {
            val sb = StringBuilder(media.type.toString())
                    .append(FILE_NAME_SEPARATOR)
                    .append(media.id)
                    .append(FILE_NAME_SEPARATOR)
                    .append(media.position)
                    .append(FILE_NAME_SEPARATOR)
                    .append(media.title)
            return sb.toString()
        }

        fun fromFileName(fileName: String): Media {
            val parts = fileName.split(FILE_NAME_SEPARATOR)
            val type = MediaType.valueOf(parts[0])
            val id = parts[1]
            val position = parts[2].toInt()
            val title = parts[3]
            return Media(id, title, type, position)
        }
    }

    class MediaTypeConverter {

        @TypeConverter
        fun mediaTypeToString(mediaType: MediaType) = mediaType.toString()

        @TypeConverter
        fun stringToMediaType(string: String) = MediaType.valueOf(string)

    }

}