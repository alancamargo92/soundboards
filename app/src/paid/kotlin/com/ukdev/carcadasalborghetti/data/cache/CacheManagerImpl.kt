package com.ukdev.carcadasalborghetti.data.cache

import android.content.Context
import com.ukdev.carcadasalborghetti.domain.cache.CacheManager
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import java.io.File
import javax.inject.Inject

private const val DIR_MEDIA = "media"
private const val DIR_AUDIOS = "$DIR_MEDIA/audios"
private const val DIR_VIDEOS = "$DIR_MEDIA/videos"

class CacheManagerImpl @Inject constructor(private val context: Context) : CacheManager {

    private val baseDir by lazy { context.filesDir.absolutePath }

    override fun clearCache() {
        val dir = File(baseDir)
        dir.deleteRecursively()
    }

    override fun getDir(mediaType: MediaType): File {
        val subDir = if (mediaType == MediaType.AUDIO) {
            DIR_AUDIOS
        } else {
            DIR_VIDEOS
        }

        return File("$baseDir/$subDir")
    }
}