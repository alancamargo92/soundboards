package com.ukdev.carcadasalborghetti.ui.di

import com.ukdev.carcadasalborghetti.ui.ads.AdLoader
import com.ukdev.carcadasalborghetti.ui.ads.AdLoaderImpl
import com.ukdev.carcadasalborghetti.ui.fragments.MediaListFragmentMapProvider
import com.ukdev.carcadasalborghetti.ui.fragments.MediaListFragmentMapProviderImpl
import com.ukdev.carcadasalborghetti.ui.media.VideoHelper
import com.ukdev.carcadasalborghetti.ui.media.VideoHelperImpl
import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider
import com.ukdev.carcadasalborghetti.ui.tools.MenuProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class FreeUiModule {

    @Binds
    @ActivityScoped
    abstract fun bindVideoHelper(impl: VideoHelperImpl): VideoHelper

    @Binds
    @ActivityScoped
    abstract fun bindMenuProvider(impl: MenuProviderImpl): MenuProvider

    @Binds
    @ActivityScoped
    abstract fun bindAdLoader(impl: AdLoaderImpl): AdLoader

    @Binds
    @ActivityScoped
    abstract fun bindFreeMediaListFragmentMapProvider(
        impl: MediaListFragmentMapProviderImpl
    ): MediaListFragmentMapProvider
}
