package com.ukdev.carcadasalborghetti.ui.di

import com.ukdev.carcadasalborghetti.ui.media.MediaHelper
import com.ukdev.carcadasalborghetti.ui.media.MediaHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class UiModule {

    @Binds
    @ActivityScoped
    abstract fun bindMediaHelper(impl: MediaHelperImpl): MediaHelper
}
