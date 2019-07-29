package com.ukdev.carcadasalborghetti.utils

import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.fragments.AudioFragment
import com.ukdev.carcadasalborghetti.fragments.MediaListFragment
import com.ukdev.carcadasalborghetti.fragments.VideoFragment

fun getFragments(): Map<Int, MediaListFragment> = mapOf(
        R.string.audios to AudioFragment(),
        R.string.videos to VideoFragment()
)