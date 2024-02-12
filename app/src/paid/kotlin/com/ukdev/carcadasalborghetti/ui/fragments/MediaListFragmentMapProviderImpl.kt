package com.ukdev.carcadasalborghetti.ui.fragments

import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.ui.model.MediaListFragmentType
import javax.inject.Inject

class MediaListFragmentMapProviderImpl @Inject constructor() : MediaListFragmentMapProvider {

    override fun provideFragmentMap(): Map<Int, MediaListFragment> = mapOf(
        R.string.audios to DefaultMediaListFragment.newInstance(MediaListFragmentType.AUDIO),
        R.string.videos to DefaultMediaListFragment.newInstance(MediaListFragmentType.VIDEO),
        R.string.favourites to DefaultMediaListFragment.newInstance(MediaListFragmentType.FAVOURITES)
    )
}
