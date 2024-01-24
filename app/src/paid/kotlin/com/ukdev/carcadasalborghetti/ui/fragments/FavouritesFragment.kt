package com.ukdev.carcadasalborghetti.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.ukdev.carcadasalborghetti.databinding.FragmentAudioBinding
import com.ukdev.carcadasalborghetti.databinding.LayoutListBinding
import com.ukdev.carcadasalborghetti.domain.entities.MediaType
import com.ukdev.carcadasalborghetti.ui.adapter.FavouritesAdapter
import com.ukdev.carcadasalborghetti.ui.adapter.MediaAdapter
import com.ukdev.carcadasalborghetti.ui.di.FavouritesHandlerDependency
import com.ukdev.carcadasalborghetti.ui.media.MediaHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesFragment : MediaListFragment(MediaType.BOTH) {

    private var _binding: FragmentAudioBinding? = null
    private val binding: FragmentAudioBinding
        get() = _binding!!

    override val baseBinding: LayoutListBinding
        get() = LayoutListBinding.bind(binding.base.root)

    @Inject
    @FavouritesHandlerDependency
    override lateinit var mediaHandler: MediaHandler

    override val adapter: MediaAdapter = FavouritesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAudioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener { mediaHandler.stop() }
    }

    override fun showFab() {
        binding.fab.isVisible = true
    }

    override fun hideFab() {
        binding.fab.isVisible = false
    }

}