package com.ukdev.carcadasalborghetti.data.di

import android.content.Context
import com.google.firebase.storage.StorageReference
import com.ukdev.carcadasalborghetti.data.local.MediaLocalDataSource
import com.ukdev.carcadasalborghetti.data.remote.MediaRemoteDataSource
import com.ukdev.carcadasalborghetti.data.tools.Logger
import com.ukdev.carcadasalborghetti.framework.local.MediaLocalDataSourceImpl
import com.ukdev.carcadasalborghetti.framework.remote.MediaRemoteDataSourceImpl
import com.ukdev.carcadasalborghetti.framework.remote.api.tools.IOHelper
import com.ukdev.carcadasalborghetti.framework.tools.PaidFileHelper
import com.ukdev.carcadasalborghetti.framework.tools.PaidFileHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaidDataSourceModule {

    @Provides
    @Singleton
    fun provideIoHelper(logger: Logger) = IOHelper(logger)

    @Provides
    @Singleton
    fun providePaidFileHelper(
        @ApplicationContext context: Context
    ): PaidFileHelper = PaidFileHelperImpl(context)

    @Provides
    @Singleton
    fun provideMediaLocalDataSource(fileHelper: PaidFileHelper): MediaLocalDataSource {
        return MediaLocalDataSourceImpl(fileHelper)
    }

    @Provides
    @Singleton
    fun provideMediaRemoteDataSource(
        storageReference: StorageReference,
        fileHelper: PaidFileHelper
    ): MediaRemoteDataSource {
        return MediaRemoteDataSourceImpl(storageReference, fileHelper)
    }
}
