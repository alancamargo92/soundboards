package com.ukdev.carcadasalborghetti.handlers

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import android.os.Environment
import androidx.core.content.FileProvider
import com.crashlytics.android.Crashlytics
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.model.Audio
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AudioHandler(callback: MediaCallback) : MediaHandler<Audio>(callback), KoinComponent {

    private val context by inject<Context>()

    private var mediaPlayer: MediaPlayer? = null

    override fun play(media: Audio) {
        mediaPlayer?.release()

        mediaPlayer = MediaPlayer.create(context, media.fileRes).apply {
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

    override fun stop() {
        mediaPlayer?.stop()
        callback.onStopPlayback()
    }

    override fun share(media: Audio) {
        val file = try {
            getFile(media)
        } catch (ex: IOException) {
            Crashlytics.log("Error creating file for ${media.title}")
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
                .putExtra(Intent.EXTRA_TEXT, media.title)

        val chooser = Intent.createChooser(shareIntent,
                context.getString(R.string.chooser_title_share))
                .addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

    private fun getFile(audio: Audio): File {
        val baseDir = "${Environment.getExternalStorageDirectory().absolutePath}/tmp_carcadas/"
        val dir = File(baseDir)
        if (!dir.exists())
            dir.mkdir()
        val audioFile = File(baseDir, "${audio.title}.mp3")

        val buffer = ByteArray(1024 * 500)
        val inputStream = context.resources.openRawResource(audio.fileRes)
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