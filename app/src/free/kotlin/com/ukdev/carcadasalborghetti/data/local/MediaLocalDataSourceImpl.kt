package com.ukdev.carcadasalborghetti.data.local

import android.content.Context
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.Media
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.File
import javax.inject.Inject

class MediaLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MediaLocalDataSource {

    override suspend fun getMediaList(mediaType: MediaType): List<Media> {
        val ids = getIds()
        val titles = getTitles()
        val idsAndTitles = ids.zip(titles) { id, title -> id to title }

        return idsAndTitles.map { (id, title) ->
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

    override fun clearCache() = Unit

    override fun createFile(media: Media): File {
        error("Free version already uses local files")
    }

    override fun getFileUri(file: File): String {
        error("Free version already uses local files")
    }

    private fun getIds(): List<String> {
        val typedArray = context.resources.obtainTypedArray(R.array.audios)
        val audioResourceIds = IntArray(typedArray.length()).also {
            it.forEachIndexed { index, _ ->
                it[index] = typedArray.getResourceId(index, 0)
            }
        }
        typedArray.recycle()

        return audioResourceIds.map { resId ->
            "android.resource://${context.packageName}/$resId"
        }
    }

    private fun getTitles(): Array<String> {
        return context.resources.getStringArray(R.array.titles)
    }
}
