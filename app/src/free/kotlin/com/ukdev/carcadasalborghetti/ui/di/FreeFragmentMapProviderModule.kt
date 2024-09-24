package com.ukdev.carcadasalborghetti.ui.di

import com.ukdev.carcadasalborghetti.ui.fragments.FragmentListProvider
import com.ukdev.carcadasalborghetti.ui.fragments.FragmentListProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class FreeFragmentMapProviderModule {

    @Binds
    @ActivityScoped
    abstract fun bindFreeMediaListFragmentMapProvider(
        impl: FragmentListProviderImpl
    ): FragmentListProvider
}
