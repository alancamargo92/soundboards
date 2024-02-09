package com.ukdev.carcadasalborghetti.data.local

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.ukdev.carcadasalborghetti.data.db.FavouritesDao
import com.ukdev.carcadasalborghetti.data.mapping.toDb
import com.ukdev.carcadasalborghetti.data.mapping.toDomain
import com.ukdev.carcadasalborghetti.domain.cache.CacheManager
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject

private const val FILE_NAME_SEPARATOR = "#"

class MediaLocalDataSourceImpl @Inject constructor(
    private val dao: FavouritesDao,
    private val cacheManager: CacheManager,
    @ApplicationContext private val context: Context
) : MediaLocalDataSource {

    override suspend fun getMediaList(mediaType: MediaType): List<Media> {
        val files = cacheManager.getDir(mediaType).listFiles()

        return files?.map { file ->
            val parts = file.name.split(FILE_NAME_SEPARATOR)
            val uri = getFileUri(file)
            val type = MediaType.valueOf(parts[0])
            val title = parts[1]

            Media(
                id = uri,
                title = title,
                type = type
            )
        } ?: throw FileNotFoundException()
    }

    override fun getFavourites(): Flow<List<Media>> {
        return dao.getFavourites().map { dbMediaList ->
            dbMediaList.map { it.toDomain() }
        }
    }

    override suspend fun saveToFavourites(media: Media) {
        val dbMedia = media.toDb()
        dao.insert(dbMedia)
    }

    override suspend fun removeFromFavourites(media: Media) {
        val dbMedia = media.toDb()
        dao.delete(dbMedia)
    }

    override suspend fun isSavedToFavourites(media: Media): Boolean {
        return dao.count(media.id) > 0
    }

    override fun createFile(media: Media): File {
        val dir = cacheManager.getDir(media.type)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        return File("$dir/${media.title}")
    }

    override fun getFileUri(file: File): String {
        if (!file.exists()) {
            throw FileNotFoundException()
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val authority = "${context.packageName}.provider"
            FileProvider.getUriForFile(context, authority, file)
        } else {
            Uri.fromFile(file)
        }.toString()
    }
}
