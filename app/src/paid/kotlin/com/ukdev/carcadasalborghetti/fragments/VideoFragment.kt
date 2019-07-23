package com.ukdev.carcadasalborghetti.fragments

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.adapter.VideoAdapter
import com.ukdev.carcadasalborghetti.handlers.VideoHandler
import com.ukdev.carcadasalborghetti.listeners.DeviceInteractionListener
import com.ukdev.carcadasalborghetti.listeners.RecyclerViewInteractionListener
import com.ukdev.carcadasalborghetti.model.Video
import com.ukdev.carcadasalborghetti.utils.hasStoragePermissions
import com.ukdev.carcadasalborghetti.utils.provideViewModel
import com.ukdev.carcadasalborghetti.utils.requestStoragePermissions
import com.ukdev.carcadasalborghetti.viewmodel.VideoViewModel
import kotlinx.android.synthetic.main.layout_list.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class VideoFragment : MediaListFragment<Video>(),
        RecyclerViewInteractionListener<Video>,
        DeviceInteractionListener {

    override val mediaHandler by inject<VideoHandler>{ parametersOf(this) }

    private val viewModel by provideViewModel(VideoViewModel::class)
    private val layoutManager by lazy { GridLayoutManager(requireContext(), SPAN_COUNT_PORTRAIT) }
    private val adapter = VideoAdapter()

    private var videos = listOf<Video>()
    private var topPosition = RECYCLER_VIEW_TOP_POSITION
    private var videoToShare: Video? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        viewModel.getVideos().observe(this, Observer { videos ->
            this.videos = videos
            adapter.setData(videos)
        })
    }

    override fun onItemClick(media: Video) {
        mediaHandler.play(media)
    }

    override fun onItemLongClick(media: Video) {
        if (SDK_INT >= M && !hasStoragePermissions()) {
            videoToShare = media
            requestStoragePermissions(REQUEST_CODE_STORAGE_PERMISSIONS)
        } else {
            mediaHandler.share(media)
        }
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
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.adapter = adapter
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                topPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
            }
        })
    }

    companion object {
        private const val RECYCLER_VIEW_TOP_POSITION = 0
        private const val REQUEST_CODE_STORAGE_PERMISSIONS = 123
        private const val SPAN_COUNT_PORTRAIT = 2
        private const val SPAN_COUNT_LANDSCAPE = 3
    }

}