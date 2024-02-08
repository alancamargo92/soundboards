package com.ukdev.carcadasalborghetti.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.ukdev.carcadasalborghetti.core.extensions.args
import com.ukdev.carcadasalborghetti.core.extensions.putArguments
import com.ukdev.carcadasalborghetti.databinding.ActivityVideoBinding
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class VideoActivity : AppCompatActivity() {

    private var _binding: ActivityVideoBinding? = null
    private val binding: ActivityVideoBinding
        get() = _binding!!

    private val args by args<Args>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureActionBar()
        startPlayback()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    private fun configureActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = args.media.title
        }
    }

    private fun startPlayback() {
        with(binding.videoView) {
            setVideoURI(args.media.uri)
            setMediaController(MediaController(context).also { it.setAnchorView(this) })
            start()
        }
    }

    @Parcelize
    data class Args(val media: UiMedia) : Parcelable

    companion object {

        fun getIntent(context: Context, media: UiMedia): Intent {
            val args = Args(media)
            return Intent(context, VideoActivity::class.java)
                .putArguments(args)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
}
