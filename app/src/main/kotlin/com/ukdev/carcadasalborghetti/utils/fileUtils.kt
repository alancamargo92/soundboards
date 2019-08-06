package com.ukdev.carcadasalborghetti.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import android.os.Environment
import androidx.core.content.FileProvider
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.model.MediaType
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class FileUtils(private val context: Context) {

    fun getFile(byteStream: InputStream?, fileName: String): File {
        val baseDir = "${Environment.getExternalStorageDirectory().absolutePath}/tmp_carcadas/"
        val dir = File(baseDir)
        if (!dir.exists())
            dir.mkdir()
        val file = File(baseDir, fileName)

        val buffer = ByteArray(1024 * 500)
        byteStream?.let { inputStream ->
            val out = FileOutputStream(file)
            var content = inputStream.read(buffer)

            while (content != -1) {
                out.write(buffer, 0, content)
                content = inputStream.read(buffer)
            }

            out.run {
                flush()
                close()
            }
        }

        return file
    }

    fun getUri(file: File): Uri = if (SDK_INT >= N) {
        val authority = "${context.packageName}.provider"
        FileProvider.getUriForFile(context, authority, file)
    } else {
        Uri.fromFile(file)
    }

    fun shareFile(uri: Uri, mediaType: MediaType) {
        val type = if (mediaType == MediaType.AUDIO) "audio/*" else "video/*"
        val shareIntent = Intent(Intent.ACTION_SEND).setType(type)
                .putExtra(Intent.EXTRA_STREAM, uri)
                .putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.subject_share))

        val chooser = Intent.createChooser(shareIntent,
                context.getString(R.string.chooser_title_share))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

}