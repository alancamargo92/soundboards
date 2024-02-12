package com.ukdev.carcadasalborghetti.ui.di

import com.ukdev.carcadasalborghetti.ui.tools.MenuProvider
import com.ukdev.carcadasalborghetti.ui.tools.MenuProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class PaidUiModule  {

    @Binds
    @ActivityScoped
    abstract fun bindMenuProvider(impl: MenuProviderImpl): MenuProvider
}
