package com.ukdev.carcadasalborghetti.data.local

import android.content.Context
import com.ukdev.carcadasalborghetti.data.db.FavouritesDaoV2
import com.ukdev.carcadasalborghetti.data.mapping.toDb
import com.ukdev.carcadasalborghetti.data.mapping.toDomain
import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MediaLocalDataSourceV2Impl @Inject constructor(
    private val dao: FavouritesDaoV2,
    @ApplicationContext private val context: Context
) : MediaLocalDataSourceV2 {

    override suspend fun getMediaList(mediaType: MediaTypeV2): List<MediaV2> {
        TODO("Not yet implemented")
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
}
