package com.ukdev.carcadasalborghetti.data.di

import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.framework.tools.FileHelper
import com.ukdev.carcadasalborghetti.framework.tools.FileHelperImpl
import com.ukdev.carcadasalborghetti.framework.tools.LoggerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideLogger(crashlytics: FirebaseCrashlytics): Logger = LoggerImpl(crashlytics)

    @Provides
    @Singleton
    fun provideFileHelper(
        @ApplicationContext context: Context
    ): FileHelper = FileHelperImpl(context)
}