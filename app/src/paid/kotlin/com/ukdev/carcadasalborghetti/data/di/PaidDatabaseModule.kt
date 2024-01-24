package com.ukdev.carcadasalborghetti.data.di

import android.content.Context
import androidx.room.Room
import com.ukdev.carcadasalborghetti.framework.local.db.DatabaseProvider
import com.ukdev.carcadasalborghetti.framework.local.db.FavouritesDao
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
}
