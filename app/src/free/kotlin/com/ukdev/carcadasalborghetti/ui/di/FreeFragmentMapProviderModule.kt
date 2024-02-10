package com.ukdev.carcadasalborghetti.ui.di

import com.ukdev.carcadasalborghetti.ui.fragments.MediaListFragmentMapProvider
import com.ukdev.carcadasalborghetti.ui.fragments.MediaListFragmentMapProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class FreeFragmentMapProviderModule {

    @Binds
    @ViewModelScoped
    abstract fun bindFreeMediaListFragmentMapProvider(
        impl: MediaListFragmentMapProviderImpl
    ): MediaListFragmentMapProvider
}
