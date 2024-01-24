package com.ukdev.carcadasalborghetti.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PagerAdapter(
    fragmentManager: FragmentManager,
    private val labelsAndFragments: Map<Int, Fragment>
) : FragmentStatePagerAdapter(fragmentManager, labelsAndFragments.size) {

    override fun getItem(position: Int): Fragment = labelsAndFragments.values.toList()[position]

    override fun getCount() = labelsAndFragments.size
}
