package com.ukdev.carcadasalborghetti.data.tools

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import androidx.core.content.FileProvider
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

private const val DIR_MEDIA = "media"
private const val DIR_AUDIOS = "$DIR_MEDIA/audios"
private const val DIR_VIDEOS = "$DIR_MEDIA/videos"
private const val EXTENSION_AUDIO = "mp3"

open class FileHelperImpl @Inject constructor(private val context: Context) : FileHelper {

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

    override suspend fun getFileUri(inputStream: InputStream?, media: Media): Uri {
        val file = saveFile(inputStream, media)
        return getFileUri(file)
    }

    override suspend fun shareFile(uri: Uri, media: Media) {
        val type = if (media.type == MediaType.AUDIO) "audio/*" else "video/*"
        val shareIntent = Intent(Intent.ACTION_SEND).setType(type)
            .putExtra(Intent.EXTRA_STREAM, uri)
            .putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.chooser_subject_share))

        val chooser = Intent.createChooser(
            shareIntent,
            context.getString(R.string.chooser_title_share)
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        withContext(Dispatchers.Main) {
            context.startActivity(chooser)
        }
    }

    override suspend fun shareFile(inputStream: InputStream?, media: Media) {
        val uri = getFileUri(inputStream, media)
        shareFile(uri, media)
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getByteStream(uri: Uri): InputStream? {
        return context.contentResolver.openInputStream(uri)
    }

    override suspend fun deleteAll() {
        val dir = File(baseDir)
        dir.deleteRecursively()
    }

    override suspend fun createFile(fileName: String, mediaType: MediaType): File {
        val dir = getDir(mediaType)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        return File("$dir/$fileName")
    }

    protected fun getFileUri(file: File): Uri {
        return if (SDK_INT >= N) {
            val authority = "${context.packageName}.provider"
            FileProvider.getUriForFile(context, authority, file)
        } else {
            Uri.fromFile(file)
        }
    }

    protected fun getDir(mediaType: MediaType): File {
        val subDir = if (mediaType == MediaType.AUDIO)
            DIR_AUDIOS
        else
            DIR_VIDEOS

        return File("$baseDir/$subDir")
    }

    protected fun composeFileName(media: Media): String {
        return Media.composeFileName(media)
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun saveFile(inputStream: InputStream?, media: Media): File {
        val dir = getDir(media.type)

        if (!dir.exists())
            dir.mkdirs()

        val fileName = composeFileName(media)
        val file = File(dir, fileName)

        withContext(Dispatchers.IO) {
            inputStream?.let { inputStream ->
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

    private fun getMediaType(fileName: String): MediaType {
        return if (fileName.endsWith(EXTENSION_AUDIO))
            MediaType.AUDIO
        else
            MediaType.VIDEO
    }

    private fun buildMedia(fileName: String): Media {
        return Media.fromFileName(fileName)
    }
}
