package com.ukdev.carcadasalborghetti.ui.di

import com.ukdev.carcadasalborghetti.ui.media.AudioHandler
import com.ukdev.carcadasalborghetti.ui.media.MediaHandler
import com.ukdev.carcadasalborghetti.ui.media.VideoHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
abstract class FreeMediaHandlerModule {

    @Binds
    @FragmentScoped
    @AudioHandlerDependency
    abstract fun bindAudioHandler(impl: AudioHandler): MediaHandler

    @Binds
    @FragmentScoped
    @VideoHandlerDependency
    abstract fun bindVideoHandler(impl: VideoHandler): MediaHandler
}
