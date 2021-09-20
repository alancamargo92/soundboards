package com.ukdev.carcadasalborghetti.framework.tools

import android.net.Uri
import com.dropbox.core.DbxDownloader
import com.dropbox.core.v2.files.FileMetadata
import com.ukdev.carcadasalborghetti.domain.entities.Media

interface PaidFileHelper : FileHelper {
    suspend fun getFileUri(downloader: DbxDownloader<FileMetadata>, media: Media): Uri
    suspend fun shareFile(downloader: DbxDownloader<FileMetadata>, media: Media)
}