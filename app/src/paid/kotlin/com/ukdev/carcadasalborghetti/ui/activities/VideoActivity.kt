package com.ukdev.carcadasalborghetti.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.ukdev.carcadasalborghetti.R
import kotlinx.android.synthetic.paid.activity_video.*

class VideoActivity : AppCompatActivity(R.layout.activity_video) {

    private val url by lazy { intent.getParcelableExtra<Uri>(EXTRA_URL) }
    private val title by lazy { intent.getStringExtra(EXTRA_TITLE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureActionBar()
        startPlayback()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return true
    }

    private fun configureActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = title?.removeSuffix(".mp4")
        }
    }

    private fun startPlayback() {
        with(video_view) {
            setVideoURI(url)
            setMediaController(MediaController(context).also { it.setAnchorView(this) })
            start()
        }
    }

    companion object {
        private const val EXTRA_TITLE = "EXTRA_TITLE"
        private const val EXTRA_URL = "EXTRA_URL"

        fun getIntent(context: Context, title: String, videoUrl: Uri): Intent {
            return Intent(context, VideoActivity::class.java)
                    .putExtra(EXTRA_TITLE, title)
                    .putExtra(EXTRA_URL, videoUrl)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

}