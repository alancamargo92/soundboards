package com.ukdev.carcadasalborghetti.core.di

import com.ukdev.carcadasalborghetti.core.tools.PreferencesManager
import com.ukdev.carcadasalborghetti.core.tools.PreferencesManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class CorePreferencesModule {

    @Binds
    @ViewModelScoped
    abstract fun bindPreferencesHelper(impl: PreferencesManagerImpl): PreferencesManager
}
