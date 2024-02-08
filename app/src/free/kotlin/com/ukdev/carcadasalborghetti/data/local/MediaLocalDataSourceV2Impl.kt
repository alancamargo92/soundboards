package com.ukdev.carcadasalborghetti.data.local

import android.content.Context
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MediaLocalDataSourceV2Impl @Inject constructor(
    @ApplicationContext private val context: Context
) : MediaLocalDataSourceV2 {

    override suspend fun getMediaList(mediaType: MediaTypeV2): List<MediaV2> {
        val ids = getIds()
        val titles = getTitles()
        val idsAndTitles = ids.zip(titles) { id, title -> id to title }

        return idsAndTitles.map { (id, title) ->
            MediaV2(
                id = id,
                title = title,
                type = mediaType
            )
        }
    }

    override fun getFavourites(): Flow<List<MediaV2>> = flowOf()

    override suspend fun saveToFavourites(media: MediaV2) = Unit

    override suspend fun removeFromFavourites(media: MediaV2) = Unit

    override suspend fun isSavedToFavourites(media: MediaV2): Boolean = false

    override fun clearCache() = Unit

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
