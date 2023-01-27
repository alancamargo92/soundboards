package com.ukdev.carcadasalborghetti.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.ui.fragments.AudioFragment
import com.ukdev.carcadasalborghetti.ui.fragments.FavouritesFragment
import com.ukdev.carcadasalborghetti.ui.fragments.VideoFragment

class PagerAdapter(
    fragmentManager: FragmentManager,
    private val tabCount: Int
) : FragmentStatePagerAdapter(fragmentManager, tabCount) {

    private val fragments: Map<Int, Fragment> = mapOf(
        R.string.audios to AudioFragment(),
        R.string.videos to VideoFragment(),
        R.string.favourites to FavouritesFragment()
    )

    override fun getItem(position: Int): Fragment = fragments.values.toList()[position]

    override fun getCount() = tabCount

}