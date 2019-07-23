package com.ukdev.carcadasalborghetti.fragments

import android.content.pm.PackageManager
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.AudioAdapter
import com.ukdev.carcadasalborghetti.listeners.DeviceInteractionListener
import com.ukdev.carcadasalborghetti.listeners.MediaCallback
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.Audio
import com.ukdev.carcadasalborghetti.utils.AudioHandler
import com.ukdev.carcadasalborghetti.utils.hasStoragePermissions
import com.ukdev.carcadasalborghetti.utils.provideViewModel
import com.ukdev.carcadasalborghetti.utils.requestStoragePermissions
import com.ukdev.carcadasalborghetti.viewmodel.AudioViewModel
import kotlinx.android.synthetic.main.fragment_audio.*
import kotlinx.android.synthetic.main.layout_list.*

class AudioFragment(
        private val audioHandler: AudioHandler
) : Fragment(), RecyclerViewInteractionListener<Audio>, MediaCallback, DeviceInteractionListener {

    private val viewModel by provideViewModel(AudioViewModel::class)
    private val layoutManager by lazy { GridLayoutManager(requireContext(), SPAN_COUNT_PORTRAIT) }
    private val adapter = AudioAdapter()

    private var audios = listOf<Audio>()
    private var topPosition = RECYCLER_VIEW_TOP_POSITION
    private var audioToShare: Audio? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_audio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        fab.setOnClickListener { audioHandler.stop() }
        fetchAudios()
    }

    override fun onPause() {
        super.onPause()
        audioHandler.stop()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSIONS == permissionsGranted)
            audioToShare?.let(audioHandler::share)
    }

    override fun onItemClick(media: Audio) {
        audioHandler.play(media)
    }

    override fun onItemLongClick(media: Audio) {
        if (SDK_INT >= M && !hasStoragePermissions()) {
            audioToShare = media
            requestStoragePermissions(REQUEST_CODE_STORAGE_PERMISSIONS)
        } else {
            audioHandler.share(media)
        }
    }

    override fun onStartPlayback() {
        fab.visibility = VISIBLE
    }

    override fun onStopPlayback() {
        fab.visibility = GONE
    }

    override fun onBackPressed(): Boolean {
        return if (topPosition == RECYCLER_VIEW_TOP_POSITION) {
            true
        } else {
            recycler_view.smoothScrollToPosition(RECYCLER_VIEW_TOP_POSITION)
            false
        }
    }

    override fun onScreenOrientationChangedToPortrait() {
        layoutManager.spanCount = SPAN_COUNT_PORTRAIT
    }

    override fun onScreenOrientationChangedToLandscape() {
        layoutManager.spanCount = SPAN_COUNT_LANDSCAPE
    }

    private fun configureRecyclerView() {
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter.apply { setListener(this@AudioFragment) }
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                topPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
            }
        })
    }

    private fun fetchAudios() {
        viewModel.getAudios().observe(this, Observer { audios ->
            this.audios = audios
            adapter.setData(audios)
        })
    }

    companion object {
        private const val RECYCLER_VIEW_TOP_POSITION = 0
        private const val REQUEST_CODE_STORAGE_PERMISSIONS = 123
        private const val SPAN_COUNT_PORTRAIT = 3
        private const val SPAN_COUNT_LANDSCAPE = 4
    }

}