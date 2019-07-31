package com.ukdev.carcadasalborghetti.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ukdev.carcadasalborghetti.R

class VideoActivity : AppCompatActivity() {

    private val url by lazy { intent.getStringExtra(EXTRA_URL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
    }

    companion object {
        private const val EXTRA_URL = "EXTRA_URL"

        fun getIntent(context: Context, videoUrl: String): Intent {
            return Intent(context, VideoActivity::class.java)
                    .putExtra(EXTRA_URL, videoUrl)
        }
    }

}