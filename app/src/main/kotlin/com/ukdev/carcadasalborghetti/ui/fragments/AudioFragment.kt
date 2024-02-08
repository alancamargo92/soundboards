package com.ukdev.carcadasalborghetti.ui.fragments

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.ukdev.carcadasalborghetti.data.tools.orFalse
import com.ukdev.carcadasalborghetti.databinding.FragmentAudioBinding
import com.ukdev.carcadasalborghetti.databinding.LayoutListBinding
import com.ukdev.carcadasalborghetti.domain.model.MediaTypeV2
import com.ukdev.carcadasalborghetti.ui.model.UiMedia
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioFragment : MediaListFragment(MediaTypeV2.AUDIO) {

    private var _binding: FragmentAudioBinding? = null
    private val binding: FragmentAudioBinding
        get() = _binding!!

    override val baseBinding: LayoutListBinding
        get() = LayoutListBinding.bind(binding.base.root)

    private var mediaPlayer: MediaPlayer? = null

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
        binding.btStop.setOnClickListener { viewModel.onStopButtonClicked() }
    }

    override fun setStopButtonVisibility(isVisible: Boolean) {
        binding.btStop.isVisible = isVisible
    }

    override fun getMediaList(isRefreshing: Boolean) {
        viewModel.getMediaList(mediaType, isRefreshing)
    }

    override fun playMedia(media: UiMedia) {
        mediaPlayer?.release()
        mediaPlayer = createMediaPlayer(media.uri)
    }

    override fun stopPlayback() {
        mediaPlayer?.stop()
    }

    private fun createMediaPlayer(uri: Uri): MediaPlayer {
        return MediaPlayer.create(requireContext(), uri).apply {
            if (this?.isPlaying.orFalse()) {
                stop()
                start()
            } else {
                start()
            }

            setOnCompletionListener {
                setStopButtonVisibility(isVisible = false)
            }
        }
    }
}
