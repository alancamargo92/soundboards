package com.ukdev.carcadasalborghetti.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ukdev.carcadasalborghetti.databinding.LayoutListBinding
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.ui.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.ui.adapter.VideoAdapter
import com.ukdev.carcadasalborghetti.ui.di.VideoHandlerDependency
import com.ukdev.carcadasalborghetti.ui.media.MediaHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VideoFragment : MediaListFragment(MediaType.VIDEO) {

    private var _binding: LayoutListBinding? = null
    private val binding: LayoutListBinding
        get() = _binding!!

    override val baseBinding: LayoutListBinding
        get() = binding

    @Inject
    @VideoHandlerDependency
    override lateinit var mediaHandler: MediaHandler

    override val adapter: MediaAdapter = VideoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun showFab() { }

    override fun hideFab() { }
}
