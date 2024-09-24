package com.ukdev.carcadasalborghetti.ui.fragments

import androidx.fragment.app.Fragment
import com.ukdev.carcadasalborghetti.ui.model.MediaListFragmentType
import javax.inject.Inject

class FragmentListProviderImpl @Inject constructor() : FragmentListProvider {

    override fun provideFragmentList(): List<Fragment> = listOf(
        DefaultMediaListFragment.newInstance(MediaListFragmentType.AUDIO),
        PaidAppPromotionFragment(),
        PaidAppPromotionFragment()
    )
}
