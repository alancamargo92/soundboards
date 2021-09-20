package com.ukdev.carcadasalborghetti.framework.tools

import android.content.Context
import android.net.Uri
import com.dropbox.core.DbxDownloader
import com.dropbox.core.v2.files.FileMetadata
import com.ukdev.carcadasalborghetti.domain.entities.Media
import java.io.File
import java.io.FileOutputStream

class PaidFileHelperImpl(context: Context) : PaidFileHelper, FileHelperImpl(context) {

    override suspend fun getFileUri(downloader: DbxDownloader<FileMetadata>, media: Media): Uri {
        val file = saveFile(downloader, media)
        return getFileUri(file)
    }

    override suspend fun shareFile(downloader: DbxDownloader<FileMetadata>, media: Media) {
        val uri = getFileUri(downloader, media)
        shareFile(uri, media)
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private fun saveFile(downloader: DbxDownloader<FileMetadata>, media: Media): File {
        val dir = getDir(media.type)

        if (!dir.exists())
            dir.mkdirs()

        val fileName = composeFileName(media)
        val file = File(dir, fileName)

        downloader.download(FileOutputStream(file))

        return file
    }

}