package com.ukdev.carcadasalborghetti.data.local

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.ukdev.carcadasalborghetti.data.db.FavouritesDaoV2
import com.ukdev.carcadasalborghetti.data.mapping.toDb
import com.ukdev.carcadasalborghetti.data.mapping.toDomain
import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject

private const val DIR_MEDIA = "media"
private const val DIR_AUDIOS = "$DIR_MEDIA/audios"
private const val DIR_VIDEOS = "$DIR_MEDIA/videos"
private const val FILE_NAME_SEPARATOR = "#"

class MediaLocalDataSourceV2Impl @Inject constructor(
    private val dao: FavouritesDaoV2,
    @ApplicationContext private val context: Context
) : MediaLocalDataSourceV2 {

    private val baseDir by lazy { context.filesDir.absolutePath }

    override suspend fun getMediaList(mediaType: MediaTypeV2): List<MediaV2> {
        val files = getDir(mediaType).listFiles()

        return files?.map { file ->
            val parts = file.name.split(FILE_NAME_SEPARATOR)
            val uri = getFileUri(file)
            val type = MediaTypeV2.valueOf(parts[0])
            val title = parts[1]

            MediaV2(
                id = uri,
                title = title,
                type = type
            )
        } ?: throw FileNotFoundException()
    }

    override fun clearCache() {
        val dir = File(baseDir)
        dir.deleteRecursively()
    }

    override fun getFavourites(): Flow<List<MediaV2>> {
        return dao.getFavourites().map { dbMediaList ->
            dbMediaList.map { it.toDomain() }
        }
    }

    override suspend fun saveToFavourites(media: MediaV2) {
        val dbMedia = media.toDb()
        dao.insert(dbMedia)
    }

    override suspend fun removeFromFavourites(media: MediaV2) {
        val dbMedia = media.toDb()
        dao.delete(dbMedia)
    }

    override suspend fun isSavedToFavourites(media: MediaV2): Boolean {
        return dao.count(media.id) > 0
    }

    private fun getDir(mediaType: MediaTypeV2): File {
        val subDir = if (mediaType == MediaTypeV2.AUDIO) {
            DIR_AUDIOS
        } else {
            DIR_VIDEOS
        }

        return File("$baseDir/$subDir")
    }

    private fun getFileUri(file: File): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val authority = "${context.packageName}.provider"
            FileProvider.getUriForFile(context, authority, file)
        } else {
            Uri.fromFile(file)
        }.toString()
    }
}
