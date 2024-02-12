package com.ukdev.carcadasalborghetti.core.di

import com.ukdev.carcadasalborghetti.core.tools.DialogueHelper
import com.ukdev.carcadasalborghetti.core.tools.DialogueHelperImpl
import com.ukdev.carcadasalborghetti.core.tools.ToastHelper
import com.ukdev.carcadasalborghetti.core.tools.ToastHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class CoreToolsModule {

    @Binds
    @ActivityScoped
    abstract fun bindDialogueHelper(impl: DialogueHelperImpl): DialogueHelper

    @Binds
    @ActivityScoped
    abstract fun bindToastHelper(impl: ToastHelperImpl): ToastHelper
}
