package com.ukdev.carcadasalborghetti.data.remote

import com.google.firebase.storage.StorageReference
import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.domain.model.MediaV2
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

private const val EXTENSION_MP3 = "mp3"
private const val DIR_AUDIO = "audios"
private const val DIR_VIDEO = "videos"

class MediaRemoteDataSourceV2Impl @Inject constructor(
    private val storageReference: StorageReference
) : MediaRemoteDataSourceV2 {

    override suspend fun getMediaList(mediaType: MediaTypeV2): List<MediaV2> {
        val dir = when (mediaType) {
            MediaTypeV2.AUDIO -> DIR_AUDIO
            MediaTypeV2.VIDEO -> DIR_VIDEO
        }

        return storageReference.child(dir).listAll().await().items.map {
            val type = if (it.name.endsWith(EXTENSION_MP3)) {
                MediaTypeV2.AUDIO
            } else {
                MediaTypeV2.VIDEO
            }

            MediaV2(
                id = it.path,
                title = it.name,
                type = type
            )
        }
    }

    override suspend fun download(media: MediaV2, destinationFile: File): File {
        val dir = when (media.type) {
            MediaTypeV2.AUDIO -> DIR_AUDIO
            MediaTypeV2.VIDEO -> DIR_VIDEO
        }

        val fileToDownload = storageReference.child(dir).child(media.title)
        fileToDownload.getFile(destinationFile).await()
        return destinationFile
    }
}
