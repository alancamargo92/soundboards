package com.ukdev.carcadasalborghetti.core.di

import com.ukdev.carcadasalborghetti.core.tools.Logger
import com.ukdev.carcadasalborghetti.core.tools.LoggerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger = LoggerImpl()
}
