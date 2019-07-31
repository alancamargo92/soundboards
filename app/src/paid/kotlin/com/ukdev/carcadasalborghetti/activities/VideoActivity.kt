package com.ukdev.carcadasalborghetti.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.ukdev.carcadasalborghetti.R
import kotlinx.android.synthetic.paid.activity_video.*

class VideoActivity : AppCompatActivity() {

    private val url by lazy { intent.getStringExtra(EXTRA_URL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        startPlayback()
    }

    private fun startPlayback() {
        video_view.run {
            setVideoURI(Uri.parse(url))
            setMediaController(MediaController(context).also { it.setAnchorView(this) })
            start()
        }
    }

    companion object {
        private const val EXTRA_URL = "EXTRA_URL"

        fun getIntent(context: Context, videoUrl: String): Intent {
            return Intent(context, VideoActivity::class.java)
                    .putExtra(EXTRA_URL, videoUrl)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

}