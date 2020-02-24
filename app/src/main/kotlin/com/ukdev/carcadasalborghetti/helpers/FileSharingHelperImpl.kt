package com.ukdev.carcadasalborghetti.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import androidx.core.content.FileProvider
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class FileSharingHelperImpl(private val context: Context) : FileSharingHelper {

    override suspend fun shareFile(
            byteStream: InputStream?,
            fileName: String,
            mediaType: MediaType
    ) {
        val uri = getFileUri(byteStream, fileName)
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

    private suspend fun getFileUri(byteStream: InputStream?, fileName: String): Uri {
        val file = getFile(byteStream, fileName)

        return if (SDK_INT >= N) {
            val authority = "${context.packageName}.provider"
            FileProvider.getUriForFile(context, authority, file)
        } else {
            Uri.fromFile(file)
        }
    }

    private suspend fun getFile(byteStream: InputStream?, fileName: String): File {
        val dir = context.filesDir
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

}