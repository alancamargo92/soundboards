package com.ukdev.carcadasalborghetti.data.di

import com.ukdev.carcadasalborghetti.data.tools.PreferencesHelper
import com.ukdev.carcadasalborghetti.framework.tools.PreferencesHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class TempDataModule {

    @Binds
    @ActivityScoped
    abstract fun bindPreferencesHelper(impl: PreferencesHelperImpl): PreferencesHelper
}
