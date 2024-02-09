package com.ukdev.carcadasalborghetti.data.remote

import com.google.firebase.storage.StorageReference
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.Media
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

private const val EXTENSION_MP3 = "mp3"
private const val DIR_AUDIO = "audios"
private const val DIR_VIDEO = "videos"

class MediaRemoteDataSourceImpl @Inject constructor(
    private val storageReference: StorageReference
) : MediaRemoteDataSource {

    override suspend fun getMediaList(mediaType: MediaType): List<Media> {
        val dir = when (mediaType) {
            MediaType.AUDIO -> DIR_AUDIO
            MediaType.VIDEO -> DIR_VIDEO
        }

        return storageReference.child(dir).listAll().await().items.map {
            val type = if (it.name.endsWith(EXTENSION_MP3)) {
                MediaType.AUDIO
            } else {
                MediaType.VIDEO
            }

            Media(
                id = it.path,
                title = it.name,
                type = type
            )
        }
    }

    override suspend fun download(media: Media, destinationFile: File): File {
        val dir = when (media.type) {
            MediaType.AUDIO -> DIR_AUDIO
            MediaType.VIDEO -> DIR_VIDEO
        }

        val fileToDownload = storageReference.child(dir).child(media.title)
        fileToDownload.getFile(destinationFile).await()
        return destinationFile
    }
}
