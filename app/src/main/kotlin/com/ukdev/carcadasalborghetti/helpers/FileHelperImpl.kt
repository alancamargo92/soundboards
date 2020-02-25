package com.ukdev.carcadasalborghetti.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import androidx.core.content.FileProvider
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.Media
import com.ukdev.carcadasalborghetti.model.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream

class FileHelperImpl(private val context: Context) : FileHelper {

    private val baseDir by lazy { context.filesDir.absolutePath }

    override suspend fun listFiles(mediaType: MediaType): List<Media> {
        val files = getDir(mediaType).listFiles()

        return files?.map { file ->
            buildMedia(file.name)
        }?.toList() ?: throw FileNotFoundException()
    }

    override suspend fun getFileUri(fileName: String): Uri {
        val mediaType = getMediaType(fileName)
        val dir = getDir(mediaType)
        val file = dir.listFiles()?.find { it.name.contains(fileName) }

        if (file != null)
            return getFileUri(file)
        else
            throw FileNotFoundException()
    }

    override suspend fun shareFile(
            byteStream: InputStream?,
            media: Media,
            mediaType: MediaType
    ) {
        val uri = getFileUri(byteStream, media, mediaType)
        val type = if (mediaType == MediaType.AUDIO) "audio/*" else "video/*"
        val shareIntent = Intent(Intent.ACTION_SEND).setType(type)
                .putExtra(Intent.EXTRA_STREAM, uri)
                .putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.subject_share))

        val chooser = Intent.createChooser(shareIntent,
                context.getString(R.string.chooser_title_share))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        withContext(Dispatchers.Main) {
            context.startActivity(chooser)
        }
    }

    override suspend fun getByteStream(uri: Uri): InputStream? {
        return context.contentResolver.openInputStream(uri)
    }

    override suspend fun deleteAll() {
        val dir = File(baseDir)
        dir.deleteRecursively()
    }

    private suspend fun getFileUri(
            byteStream: InputStream?,
            media: Media,
            mediaType: MediaType
    ): Uri {
        val file = saveFile(byteStream, media, mediaType)
        return getFileUri(file)
    }

    private suspend fun saveFile(
            byteStream: InputStream?,
            media: Media,
            mediaType: MediaType
    ): File {
        val dir = getDir(mediaType)

        if (!dir.exists())
            dir.mkdirs()

        val fileName = composeFileName(media)
        val file = File(dir, fileName)

        withContext(Dispatchers.IO) {
            byteStream?.let { inputStream ->
                FileOutputStream(file).use { out ->
                    val buffer = ByteArray(inputStream.available())
                    var content = inputStream.read(buffer)

                    while (content != -1) {
                        out.write(buffer, 0, content)
                        content = inputStream.read(buffer)
                    }

                    out.flush()
                }
            }
        }

        return file
    }

    private fun getFileUri(file: File): Uri {
        return if (SDK_INT >= N) {
            val authority = "${context.packageName}.provider"
            FileProvider.getUriForFile(context, authority, file)
        } else {
            Uri.fromFile(file)
        }
    }

    private fun getMediaType(fileName: String): MediaType {
        return if (fileName.endsWith(EXTENSION_AUDIO))
            MediaType.AUDIO
        else
            MediaType.VIDEO
    }

    private fun getDir(mediaType: MediaType): File {
        val subDir = if (mediaType == MediaType.AUDIO)
            DIR_AUDIOS
        else
            DIR_VIDEOS

        return File("$baseDir/$subDir")
    }

    // FIXME: adapt to free flavour
    private fun composeFileName(media: Media): String {
        return "${media.id}$FILE_NAME_SEPARATOR${media.position}$FILE_NAME_SEPARATOR${media.title}"
    }

    // FIXME: adapt to free flavour
    private fun buildMedia(fileName: String): Media {
        val parts = fileName.split(FILE_NAME_SEPARATOR)
        val id = parts[0]
        val position = parts[1].toInt()
        val title = parts[2]
        return Media(id, title).also {
            it.position = position
        }
    }

    private companion object {
        const val DIR_MEDIA = "media"
        const val DIR_AUDIOS = "$DIR_MEDIA/audios"
        const val DIR_VIDEOS = "$DIR_MEDIA/videos"
        const val EXTENSION_AUDIO = "mp3"
        const val FILE_NAME_SEPARATOR = '#'
    }

}