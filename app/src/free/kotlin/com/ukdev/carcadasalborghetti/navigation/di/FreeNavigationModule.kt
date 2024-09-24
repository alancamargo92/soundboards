package com.ukdev.carcadasalborghetti.navigation.di

import com.ukdev.carcadasalborghetti.navigation.VideoActivityNavigation
import com.ukdev.carcadasalborghetti.navigation.VideoActivityNavigationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
abstract class FreeNavigationModule {

    @Binds
    @FragmentScoped
    abstract fun bindVideoActivityNavigation(
        impl: VideoActivityNavigationImpl
    ): VideoActivityNavigation
}
