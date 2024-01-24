package com.ukdev.carcadasalborghetti.framework.remote

import com.google.firebase.storage.StorageReference
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.domain.entities.Media
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.framework.remote.api.DIR_AUDIO
import com.ukdev.carcadasalborghetti.framework.remote.api.DIR_VIDEO
import com.ukdev.carcadasalborghetti.framework.tools.FileHelper
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

private const val EXTENSION_MP3 = "mp3"

class MediaRemoteDataSourceImpl @Inject constructor(
    private val storageReference: StorageReference,
    private val fileHelper: FileHelper
) : MediaRemoteDataSource {

    override suspend fun listMedia(mediaType: MediaType): List<Media> {
        val dir = when (mediaType) {
            MediaType.AUDIO -> DIR_AUDIO
            MediaType.VIDEO -> DIR_VIDEO
            else -> throw IllegalArgumentException("Must be either audio or video")
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

    override suspend fun download(mediaId: String): File {
        val fileName = mediaId.split("/").last()
        val extension = fileName.split(".").last()

        val mediaType = if (extension == EXTENSION_MP3) {
            MediaType.AUDIO
        } else {
            MediaType.VIDEO
        }

        val file = fileHelper.createFile(fileName, mediaType)
        storageReference.child(mediaId).getFile(file).await()

        return file
    }

}