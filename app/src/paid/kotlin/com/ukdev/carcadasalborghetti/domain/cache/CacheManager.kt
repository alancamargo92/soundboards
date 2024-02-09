package com.ukdev.carcadasalborghetti.domain.cache

import com.ukdev.carcadasalborghetti.domain.model.MediaType
import java.io.File

interface CacheManager {

    fun clearCache()

    fun getDir(mediaType: MediaType): File
}
