package com.ukdev.carcadasalborghetti.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    private var fragmentMap: Map<Int, Fragment> = emptyMap()

    fun submitFragmentMap(fragmentMap: Map<Int, Fragment>) {
        this.fragmentMap = fragmentMap
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment = fragmentMap.values.toList()[position]

    override fun getCount() = fragmentMap.size
}
