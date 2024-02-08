package com.ukdev.carcadasalborghetti.core.di

import com.ukdev.carcadasalborghetti.core.tools.PreferencesHelper
import com.ukdev.carcadasalborghetti.core.tools.PreferencesHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class TempCoreModule {

    @Binds
    @ActivityScoped
    abstract fun bindPreferencesHelper(impl: PreferencesHelperImpl): PreferencesHelper
}
