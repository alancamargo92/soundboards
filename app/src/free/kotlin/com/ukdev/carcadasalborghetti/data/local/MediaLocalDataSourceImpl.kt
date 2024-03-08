package com.ukdev.carcadasalborghetti.data.local

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class MediaLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MediaLocalDataSource {

    override suspend fun getMediaList(mediaType: MediaType): List<Media> {
        val ids = getIds()
        val titles = context.resources.getStringArray(R.array.titles)

        return ids.zip(titles) { id, title ->
            Media(
                id = id,
                title = title,
                type = mediaType
            )
        }
    }

    override fun getFavourites(): Flow<List<Media>> = flowOf()

    override suspend fun saveToFavourites(media: Media) = Unit

    override suspend fun removeFromFavourites(media: Media) = Unit

    override suspend fun isSavedToFavourites(media: Media): Boolean = false

    override fun createFile(media: Media): File {
        val baseDir = context.filesDir.absolutePath
        val dir = File("$baseDir/audios")
        if (!dir.exists()) {
            dir.mkdirs()
        }

        return File(dir, media.title).also {
            val output = FileOutputStream(it)
            val uri = media.id.toUri()
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.copyTo(output)
        }
    }

    override fun getFileUri(file: File): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val authority = "${context.packageName}.provider"
            FileProvider.getUriForFile(context, authority, file)
        } else {
            Uri.fromFile(file)
        }.toString()
    }

    private fun getIds(): List<String> {
        val typedArray = context.resources.obtainTypedArray(R.array.audios)
        val audioResourceIds = IntArray(typedArray.length()).also {
            it.forEachIndexed { index, _ ->
                it[index] = typedArray.getResourceId(index, 0)
            }
        }
        typedArray.recycle()

        return audioResourceIds.map { resId -> "android.resource://${context.packageName}/$resId" }
    }
}
