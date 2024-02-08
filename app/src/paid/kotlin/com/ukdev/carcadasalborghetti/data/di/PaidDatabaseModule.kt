package com.ukdev.carcadasalborghetti.data.di

import android.content.Context
import androidx.room.Room
import com.ukdev.carcadasalborghetti.data.db.DatabaseProvider
import com.ukdev.carcadasalborghetti.data.db.DatabaseProviderV2
import com.ukdev.carcadasalborghetti.data.db.FavouritesDao
import com.ukdev.carcadasalborghetti.data.db.FavouritesDaoV2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaidDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseProvider(
        @ApplicationContext context: Context
    ): DatabaseProvider {
        return Room.databaseBuilder(
            context,
            DatabaseProvider::class.java,
            "favourites_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideFavouritesDao(provider: DatabaseProvider): FavouritesDao {
        return provider.getFavouritesDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseProviderV2(
        @ApplicationContext context: Context
    ): DatabaseProviderV2 {
        return Room.databaseBuilder(
            context,
            DatabaseProviderV2::class.java,
            "favourites-database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideFavouritesDaoV2(provider: DatabaseProviderV2): FavouritesDaoV2 {
        return provider.getFavouritesDao()
    }
}
