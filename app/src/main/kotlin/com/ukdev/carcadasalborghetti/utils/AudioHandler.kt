package com.ukdev.carcadasalborghetti.utils

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import android.os.Environment
import androidx.annotation.RawRes
import androidx.core.content.FileProvider
import com.crashlytics.android.Crashlytics
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.listeners.AudioCallback
import com.ukdev.carcadasalborghetti.model.Carcada
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AudioHandler(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    @RawRes
    private var audioFileRes: Int? = null

    fun play(@RawRes audioFileRes: Int, callback: AudioCallback) {
        mediaPlayer?.release()
        this.audioFileRes = audioFileRes

        mediaPlayer = MediaPlayer.create(context, audioFileRes).apply {
            if (isPlaying) {
                stop()
                callback.onStopPlayback()
                start()
                callback.onStartPlayback()
            } else {
                start()
                callback.onStartPlayback()
            }

            setOnCompletionListener { callback.onStopPlayback() }
        }
    }

    fun stop(callback: AudioCallback) {
        mediaPlayer?.stop()
        callback.onStopPlayback()
    }

    fun share(carcada: Carcada) {
        val file = try {
            getFile(carcada)
        } catch (ex: IOException) {
            Crashlytics.log("Error creating file for ${carcada.title}")
            Crashlytics.logException(ex)
            return
        }

        val uri = if (SDK_INT >= N) {
            val authority = "${context.packageName}.provider"
            FileProvider.getUriForFile(context, authority, file)
        } else {
            Uri.fromFile(file)
        }

        val shareIntent = Intent(Intent.ACTION_SEND).setType("audio/*")
                .putExtra(Intent.EXTRA_STREAM, uri)
                .putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.subject_share))
                .putExtra(Intent.EXTRA_TEXT, carcada.title)

        val chooser = Intent.createChooser(shareIntent,
                context.getString(R.string.chooser_title_share))
        context.startActivity(chooser)
    }

    private fun getFile(carcada: Carcada): File {
        val baseDir = "${Environment.getExternalStorageDirectory().absolutePath}/tmp_carcadas/"
        val dir = File(baseDir)
        if (!dir.exists())
            dir.mkdir()
        val audioFile = File(baseDir, "${carcada.title}.mp3")

        val buffer = ByteArray(1024 * 500)
        val inputStream = context.resources.openRawResource(carcada.audioFileRes)
        val out = FileOutputStream(audioFile)
        var content = inputStream.read(buffer)

        while (content != -1) {
            out.write(buffer, 0, content)
            content = inputStream.read(buffer)
        }

        out.run {
            flush()
            close()
        }

        return audioFile
    }

}