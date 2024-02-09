package com.ukdev.carcadasalborghetti.domain.di

import android.content.Context
import com.ukdev.carcadasalborghetti.data.cache.CacheManagerImpl
import com.ukdev.carcadasalborghetti.domain.cache.CacheManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaidCacheModule {

    @Provides
    @Singleton
    fun provideCacheManager(@ApplicationContext context: Context): CacheManager {
        return CacheManagerImpl(context)
    }
}
