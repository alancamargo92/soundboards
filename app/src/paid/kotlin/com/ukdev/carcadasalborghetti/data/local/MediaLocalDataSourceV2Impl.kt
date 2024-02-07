package com.ukdev.carcadasalborghetti.data.local

import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaLocalDataSourceV2Impl @Inject constructor(
) : MediaLocalDataSourceV2 {

    override suspend fun getMediaList(mediaType: MediaTypeV2): List<MediaV2> {
        TODO("Not yet implemented")
    }

    override fun getFavourites(): Flow<List<MediaV2>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveToFavourites(media: MediaV2) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavourites(media: MediaV2) {
        TODO("Not yet implemented")
    }

    override suspend fun isSavedToFavourites(media: MediaV2): Boolean {
        TODO("Not yet implemented")
    }
}
