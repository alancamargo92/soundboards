package com.ukdev.carcadasalborghetti.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ukdev.carcadasalborghetti.fragments.AudioFragment
import com.ukdev.carcadasalborghetti.fragments.VideoFragment
import com.ukdev.carcadasalborghetti.utils.AudioHandler

class PagerAdapter(
        fragmentManager: FragmentManager,
        private val tabCount: Int,
        private val audioHandler: AudioHandler
) : FragmentStatePagerAdapter(fragmentManager, tabCount) {

    private val audioFragment by lazy { AudioFragment(audioHandler) }
    private val videoFragment by lazy { VideoFragment() }

    override fun getItem(position: Int) = when(position) {
        POSITION_AUDIOS -> audioFragment
        POSITION_VIDEOS -> videoFragment
        else -> audioFragment
    }

    override fun getCount() = tabCount

    companion object {
        private const val POSITION_AUDIOS = 0
        private const val POSITION_VIDEOS = 1
    }

}