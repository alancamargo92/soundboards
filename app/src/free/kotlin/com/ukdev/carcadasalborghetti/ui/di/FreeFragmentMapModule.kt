package com.ukdev.carcadasalborghetti.ui.di

import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.ui.fragments.DefaultMediaListFragment
import com.ukdev.carcadasalborghetti.ui.fragments.MediaListFragment
import com.ukdev.carcadasalborghetti.ui.fragments.PaidAppPromotionFragment
import com.ukdev.carcadasalborghetti.ui.model.MediaListFragmentType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class FreeFragmentMapModule {

    @Provides
    @ActivityScoped
    fun provideFreeFragmentMap(): Map<Int, MediaListFragment> = mapOf(
        R.string.audios to DefaultMediaListFragment.newInstance(MediaListFragmentType.AUDIO),
        R.string.videos to PaidAppPromotionFragment(),
        R.string.favourites to PaidAppPromotionFragment()
    )
}
