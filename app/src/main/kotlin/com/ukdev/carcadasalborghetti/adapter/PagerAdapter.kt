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

    override fun getItem(position: Int) = when(position) {
        POSITION_AUDIOS -> AudioFragment(audioHandler)
        POSITION_VIDEOS -> VideoFragment()
        else -> AudioFragment(audioHandler)
    }

    override fun getCount() = tabCount

    companion object {
        private const val POSITION_AUDIOS = 0
        private const val POSITION_VIDEOS = 1
    }

}