package com.ukdev.carcadasalborghetti.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ukdev.carcadasalborghetti.utils.getFragments

class PagerAdapter(
        fragmentManager: FragmentManager,
        private val tabCount: Int
) : FragmentStatePagerAdapter(fragmentManager, tabCount) {

    override fun getItem(position: Int): Fragment = getFragments().values.toList()[position]

    override fun getCount() = tabCount

}